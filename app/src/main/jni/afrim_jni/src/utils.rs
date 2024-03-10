use jni::{
    objects::{JClass, JString, JValue},
    JNIEnv,
};

pub struct AndroidLogger<'a> {
    /// JNI Environement.
    env: JNIEnv<'a>,
    /// Reference to the android.util.Log class.
    log_class: JClass<'a>,
    /// Tag for log messages.
    tag: JString<'a>,
}

impl<'a> AndroidLogger<'a> {
    pub fn new(mut env: JNIEnv<'a>, tag: &str) -> Self {
        // Should not panic!
        Self {
            log_class: env.find_class("android/util/Log").unwrap(),
            tag: env.new_string(tag).unwrap(),
            env,
        }
    }

    /// Prints a message at the specific level.
    unsafe fn log(&mut self, level: &str, message: impl AsRef<str>) {
        // Should not panic!
        self.env
            .call_static_method(
                &self.log_class,
                level,
                "(Ljava/lang/String;Ljava/lang/String;)I",
                &[
                    JValue::Object(&self.tag),
                    JValue::Object(&self.env.new_string(message).unwrap()),
                ],
            )
            .unwrap();
    }

    /// Prints a message at the debug level.
    pub unsafe fn d(&mut self, message: impl AsRef<str>) {
        self.log("d", message);
    }

    /// Prints a message at the error level.
    pub unsafe fn e(&mut self, message: impl AsRef<str>) {
        self.log("e", message);
    }

    /// Prints a message at the info level.
    pub unsafe fn i(&mut self, message: impl AsRef<str>) {
        self.log("i", message);
    }

    /// Prints a message at the warning level.
    pub unsafe fn w(&mut self, message: impl AsRef<str>) {
        self.log("w", message);
    }
}
