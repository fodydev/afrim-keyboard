use jni::{
    errors::Result,
    jni_sig, jni_str,
    objects::{JClass, JString, JValue},
    strings::JNIStr,
    Env,
};

pub struct AndroidLogger<'local> {
    /// Reference to the android.util.Log class.
    log_class: JClass<'local>,
    /// Tag for log messages.
    tag: JString<'local>,
}

impl<'local> AndroidLogger<'local> {
    pub fn new(env: &mut Env<'local>, tag: &str) -> Result<Self> {
        Ok(Self {
            log_class: env.find_class(jni_str!("android/util/Log"))?,
            tag: env.new_string(tag)?,
        })
    }

    /// Prints a message at the specific level.
    fn log(&self, env: &mut Env<'local>, level: &JNIStr, message: impl AsRef<str>) -> Result<()> {
        let message = env.new_string(message)?;
        env.call_static_method(
            &self.log_class,
            level,
            jni_sig!("(Ljava/lang/String;Ljava/lang/String;)I"),
            &[JValue::Object(&self.tag), JValue::Object(&message)],
        )?;

        Ok(())
    }

    /// Prints a message at the debug level.
    pub fn d(&self, env: &mut Env<'local>, message: impl AsRef<str>) -> Result<()> {
        self.log(env, jni_str!("d"), message)
    }

    /// Prints a message at the error level.
    pub fn e(&self, env: &mut Env<'local>, message: impl AsRef<str>) -> Result<()> {
        self.log(env, jni_str!("e"), message)
    }

    /// Prints a message at the info level.
    pub fn i(&self, env: &mut Env<'local>, message: impl AsRef<str>) -> Result<()> {
        self.log(env, jni_str!("i"), message)
    }

    /// Prints a message at the warning level.
    pub fn w(&self, env: &mut Env<'local>, message: impl AsRef<str>) -> Result<()> {
        self.log(env, jni_str!("w"), message)
    }
}
