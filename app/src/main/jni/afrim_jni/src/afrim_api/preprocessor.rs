#![deny(missing_docs)]
/// Binding of the afrim preprocessor.
///
use afrim_preprocessor::Preprocessor as NativePreprocessor;
use indexmap::IndexMap;

pub struct Preprocessor {
    native: NativePreprocessor,
}

impl Preprocessor {
    /// Initializes the preprocessor.
    pub fn new(data: IndexMap<String, String>, buffer_size: usize) -> Result<Self, String> {
        let data = data
            .iter()
            .map(|(key, value)| vec![key.as_str(), value.as_str()])
            .collect();
        let map = utils::build_map(data);

        Ok(Self {
            native: NativePreprocessor::new(map.into(), buffer_size),
        })
    }

    /// Process the keyboard event.
    pub fn process_key(&mut self, key: &str, state: &str) -> Result<(bool, bool), String> {
        let key_event = utils::parse_event(key, state)?;
        let status = self.native.process(key_event);

        Ok(status)
    }

    /// Commits the text.
    pub fn commit_text(&mut self, text: String) {
        self.native.commit(&text);
    }

    /// Returns the next command to be executed.
    pub fn pop_queue(&mut self) -> String {
        self.native
            .pop_queue()
            .map(utils::parse_command)
            .unwrap_or("\"NOP\"".to_owned())
    }

    /// Returns the input from the memory.
    pub fn get_input(&self) -> String {
        self.native.get_input()
    }

    /// Clears the preprocessor commands from the stack.
    pub fn clear_queue(&mut self) {
        self.native.clear_queue();
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

    /// Converts a preprocessor command to speudo code.
    pub fn parse_command(command: Command) -> String {
        serde_json::to_string(&command).unwrap()
    }
}
