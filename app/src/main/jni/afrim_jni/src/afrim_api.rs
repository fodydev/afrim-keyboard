#![deny(missing_docs)]
/// Binding of the afrim api.
///
use afrim_config::Config;
use afrim_preprocessor::Preprocessor;
use afrim_translator::Translator;
use once_cell::sync::Lazy;
use std::path::Path;

pub struct Afrim {
    preprocessor: Preprocessor,
    translator: Translator,
}

impl Afrim {
    /// Initializes an Afrim instance based on the provided configuration file.
    pub fn from_config(config_file: &str) -> Result<Self, String> {
        let config_file = Path::new(&config_file);
        let config = Config::from_file(config_file).map_err(|err| err.to_string())?;

        // Core
        let auto_commit = config
            .core
            .as_ref()
            .and_then(|c| c.auto_commit)
            .unwrap_or(false);
        let buffer_size = config
            .core
            .as_ref()
            .and_then(|c| c.buffer_size)
            .unwrap_or(64);

        // Data
        let data = config.extract_data();
        let data = data
            .iter()
            .map(|(key, value)| vec![key.as_str(), value.as_str()])
            .collect();
        let map = utils::build_map(data);
        let preprocessor = Preprocessor::new(map.into(), buffer_size);

        // Translation
        let translation = config.extract_translation();
        #[cfg(feature = "rhai")]
        let mut translator = Translator::new(translation, auto_commit);
        #[cfg(not(feature = "rhai"))]
        let translator = Translator::new(translation, auto_commit);

        // Translators
        #[cfg(feature = "rhai")]
        config
            .extract_translators()
            .map_err(|err| err.to_string())?
            .into_iter()
            .for_each(|(name, ast)| {
                translator.register(name, ast);
            });

        Ok(Self {
            preprocessor,
            translator,
        })
    }

    /// Returns predicates base on a input.
    pub fn translate_text(&self, input: &str) -> Vec<[String; 4]> {
        self.translator
            .translate(input)
            .into_iter()
            .map(|e| [e.0, e.1, e.2.join("|"), e.3.to_string()])
            .collect()
    }

    /// Process the keyboard event.
    pub fn process_key(&mut self, key: &str, state: &str) -> Result<(bool, bool), String> {
        let key_event = utils::parse_event(key, state)?;
        let status = self.preprocessor.process(key_event);

        Ok(status)
    }

    /// Commits the text.
    pub fn commit_text(&mut self, text: String) {
        self.preprocessor.commit(text);
    }

    /// Returns the next command to be executed.
    pub fn next_command(&mut self) -> String {
        self.preprocessor
            .pop_queue()
            .map(utils::parse_command)
            .unwrap_or("\"NOP\"".to_owned())
    }

    /// Returns the input from the memory.
    pub fn get_input(&self) -> String {
        self.preprocessor.get_input()
    }

    /// Clears the afrim.
    pub fn clear(&mut self) {
        self.preprocessor.commit("".to_owned());
        self.preprocessor.clear_queue();
    }
}

/// A pointer to an afrim instance.
pub struct Singleton;

impl Singleton {
    /// Initializes a pointer to an afrim instance.
    ///
    /// Note that the resulting singletion is not thread safe.
    pub fn init_afrim() -> usize {
        static CURRENT: Lazy<usize> = Lazy::new(|| {
            let instance_ptr = Box::into_raw(Box::new(None::<Afrim>));

            instance_ptr as usize
        });

        *CURRENT
    }

    /// Returns the current afrim instance pointer.
    pub fn get_afrim() -> *mut Option<Afrim> {
        Self::init_afrim() as *mut Option<Afrim>
    }

    /// Updates the current afrim instance.
    pub fn update_afrim(afrim: Afrim) {
        let instance_ptr = Self::get_afrim();

        unsafe {
            *instance_ptr = Some(afrim);
        }
    }

    /// Drop the current afrim instance.
    ///
    /// Note that this action will free the memory, and is irreversible.
    pub unsafe fn drop_afrim() {
        let instance_ptr = Self::get_afrim();

        drop(Box::from_raw(instance_ptr));
    }
}

mod utils {
    pub use afrim_preprocessor::utils::*;
    use afrim_preprocessor::{Command, Key, KeyboardEvent};
    use serde_json::{self};
    use std::str::FromStr;

    /// Deserializes the KeyboardEvent.
    pub fn parse_event(key: &str, state: &str) -> Result<KeyboardEvent, String> {
        let event = KeyboardEvent {
            key: Key::from_str(key).map_err(|err| {
                format!("[preprocessor] Unrecognized key `{key}`.\nCaused by:\n\t{err}.")
            })?,
            state: serde_json::from_str(state).map_err(|err| {
                format!("[preprocessor] Unrecognized state `{state}`.\nCaused by:\n\t{err}.")
            })?,
            ..Default::default()
        };

        Ok(event)
    }

    /// Converts an afrim command to speudo code.
    pub fn parse_command(command: Command) -> String {
        serde_json::to_string(&command).unwrap()
    }
}
