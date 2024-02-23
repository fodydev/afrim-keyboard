#![deny(missing_docs)]
mod preprocessor;
mod service;
mod translator;

pub use crate::afrim_api::service::{Afrim, Singleton};
