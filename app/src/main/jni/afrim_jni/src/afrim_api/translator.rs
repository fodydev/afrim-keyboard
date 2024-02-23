#![deny(missing_docs)]
/// Binding of the afrim translator.
///
use afrim_translator::Translator as NativeTranslator;
use indexmap::IndexMap;
use rhai::AST;

/// Core structure of the translator.
pub struct Translator {
    native: NativeTranslator,
}

impl Translator {
    /// Initializes the translator.
    pub fn new(dictionary: IndexMap<String, Vec<String>>, auto_commit: bool) -> Self {
        Self {
            native: NativeTranslator::new(dictionary, auto_commit),
        }
    }

    /// Registers a translator from source code.
    pub fn register(&mut self, name: String, ast: AST) {
        self.native.register(name, ast);
    }

    /// Unregisters a translator.
    pub fn unregister(&mut self, name: &str) {
        self.native.unregister(name);
    }

    /// Generates predicates based on the input.
    pub fn translate(&self, input: &str) -> Vec<Vec<String>> {
        self.native
            .translate(input)
            .into_iter()
            .map(|e| vec![e.0, e.1, e.2.join("\n"), e.3.to_string()])
            .collect()
    }
}
