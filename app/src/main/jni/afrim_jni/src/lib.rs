mod afrim_api;
mod utils;

#[allow(non_snake_case)]
mod android {
    use crate::afrim_api::{Afrim, Singleton};
    use crate::utils::AndroidLogger;
    use jni::{
        errors::{Result, ThrowRuntimeExAndDefault},
        objects::{JBooleanArray, JClass, JObject, JObjectArray, JString},
        sys::{jboolean, jbooleanArray, jobjectArray, jstring},
        EnvUnowned,
    };

    const TAG: &str = "libafrim_jni";
    const AFRIM_NOT_CONFIGURED: &str = "Afrim singleton is not yet configured.";

    // Singleton
    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeInit<'local>(
        mut env: EnvUnowned<'local>,
        _class: JClass<'local>,
    ) {
        env.with_env(|env| -> Result<()> {
            let log = AndroidLogger::new(env, TAG)?;
            log.d(env, "Initializing the afrim singleton.")?;

            Singleton::init_afrim();
            log.i(env, "Afrim singleton initialized!")
        })
        .resolve::<ThrowRuntimeExAndDefault>()
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeIsInit<'local>(
        mut env: EnvUnowned<'local>,
        _class: JClass<'local>,
    ) -> jboolean {
        env.with_env(|env| -> Result<jboolean> {
            let log = AndroidLogger::new(env, TAG)?;
            log.d(env, "Checking the presence of the afrim singleton.")?;

            Ok((*Singleton::get_afrim()).is_some())
        })
        .resolve::<ThrowRuntimeExAndDefault>()
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeUpdateConfig<'local>(
        mut env: EnvUnowned<'local>,
        _class: JClass<'local>,
        config_file: JString<'local>,
    ) -> jboolean {
        env.with_env(|env| -> Result<jboolean> {
            let log = AndroidLogger::new(env, TAG)?;

            let config_file = config_file.try_to_string(env)?;
            log.d(
                env,
                format!("Updating the afrim singleton using config_file={config_file}."),
            )?;

            match Afrim::from_config(&config_file) {
                Ok(new_afrim) => {
                    Singleton::update_afrim(new_afrim);
                    log.i(env, "Afrim singleton updated!")?;

                    Ok(true)
                }
                Err(err) => {
                    log.e(
                        env,
                        format!("Error while the updating of the afrim singleton: {err:?}"),
                    )?;

                    Ok(false)
                }
            }
        })
        .resolve::<ThrowRuntimeExAndDefault>()
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeDrop<'local>(
        mut env: EnvUnowned<'local>,
        _class: JClass<'local>,
    ) {
        env.with_env(|env| -> Result<()> {
            let log = AndroidLogger::new(env, TAG)?;
            log.d(env, "Dropping the afrim singleton.")?;

            Singleton::drop_afrim();
            log.i(env, "Afrim singleton dropped!")
        })
        .resolve::<ThrowRuntimeExAndDefault>()
    }

    // Preprocessor
    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeProcessKey<'local>(
        mut env: EnvUnowned<'local>,
        _class: JClass<'local>,
        key: JString<'local>,
        state: JString<'local>,
    ) -> jbooleanArray {
        env.with_env(|env| -> Result<JObject<'local>> {
            let log = AndroidLogger::new(env, TAG)?;

            let key = key.try_to_string(env)?;
            let state = state.try_to_string(env)?;
            log.d(env, format!("Processing key={key} state={state}."))?;

            let afrim_ptr = Singleton::get_afrim();
            if let Some(afrim) = (*afrim_ptr).as_mut() {
                match afrim.process_key(&key, &state) {
                    Ok((changed, committed)) => {
                        let status = JBooleanArray::new(env, 2)?;
                        status.set_region(env, 0, &[changed, committed])?;
                        log.i(env, "Key processed!")?;

                        return Ok(status.into());
                    }
                    Err(err) => {
                        log.e(env, format!("Error while key processing: {err:?}"))?;
                    }
                }
            } else {
                log.w(env, AFRIM_NOT_CONFIGURED)?;
            };

            Ok(JObject::null())
        })
        .resolve::<ThrowRuntimeExAndDefault>()
        .into_raw()
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeCommitText<'local>(
        mut env: EnvUnowned<'local>,
        _class: JClass<'local>,
        text: JString<'local>,
    ) {
        env.with_env(|env| -> Result<()> {
            let log = AndroidLogger::new(env, TAG)?;

            let text = text.try_to_string(env)?;
            log.d(env, format!("Commiting text={text}"))?;

            let afrim_ptr = Singleton::get_afrim();
            if let Some(afrim) = (*afrim_ptr).as_mut() {
                afrim.commit_text(text);
                log.i(env, "Text committed!")
            } else {
                log.w(env, AFRIM_NOT_CONFIGURED)
            }
        })
        .resolve::<ThrowRuntimeExAndDefault>()
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeNextCommand<'local>(
        mut env: EnvUnowned<'local>,
        _class: JClass<'local>,
    ) -> jstring {
        env.with_env(|env| -> Result<JObject<'local>> {
            let log = AndroidLogger::new(env, TAG)?;
            log.d(env, "Getting the afrim command.")?;

            let afrim_ptr = Singleton::get_afrim();
            if let Some(afrim) = (*afrim_ptr).as_mut() {
                let cmd = afrim.next_command();
                let cmd = env.new_string(&cmd)?;
                log.i(env, "Afrim command got!")?;

                Ok(cmd.into())
            } else {
                log.w(env, AFRIM_NOT_CONFIGURED)?;

                Ok(JObject::null())
            }
        })
        .resolve::<ThrowRuntimeExAndDefault>()
        .into_raw()
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeClear<'local>(
        mut env: EnvUnowned<'local>,
        _class: JClass<'local>,
    ) {
        env.with_env(|env| -> Result<()> {
            let log = AndroidLogger::new(env, TAG)?;
            log.d(env, "Clearing the afrim memory.")?;

            let afrim_ptr = Singleton::get_afrim();
            if let Some(afrim) = (*afrim_ptr).as_mut() {
                afrim.clear();
                log.i(env, "Afrim memory cleared!")
            } else {
                log.w(env, AFRIM_NOT_CONFIGURED)
            }
        })
        .resolve::<ThrowRuntimeExAndDefault>()
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeGetInput<'local>(
        mut env: EnvUnowned<'local>,
        _class: JClass<'local>,
    ) -> jstring {
        env.with_env(|env| -> Result<JObject<'local>> {
            let log = AndroidLogger::new(env, TAG)?;
            log.d(env, "Getting of the afrim input text.")?;

            let afrim_ptr = Singleton::get_afrim();
            if let Some(afrim) = (*afrim_ptr).as_mut() {
                let input = afrim.get_input();
                let input = env.new_string(input)?;
                log.i(env, "Afrim input text got!")?;

                Ok(input.into())
            } else {
                log.w(env, AFRIM_NOT_CONFIGURED)?;

                Ok(JObject::null())
            }
        })
        .resolve::<ThrowRuntimeExAndDefault>()
        .into_raw()
    }

    // Translator
    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeTranslateText<'local>(
        mut env: EnvUnowned<'local>,
        _class: JClass<'local>,
    ) -> jobjectArray {
        env.with_env(|env| -> Result<JObject<'local>> {
            let log = AndroidLogger::new(env, TAG)?;
            log.d(env, "Getting of the afrim suggestions.")?;

            let afrim_ptr = Singleton::get_afrim();
            if let Some(afrim) = (*afrim_ptr).as_mut() {
                let input = afrim.get_input();
                log.d(env, format!("input={input}"))?;
                let predicates = afrim.translate_text(&input);

                let array = JObjectArray::<JObject>::new(env, predicates.len(), JObject::null())?;

                for (id, predicate) in predicates.iter().enumerate() {
                    let subarray =
                        JObjectArray::<JString>::new(env, predicate.len(), JString::null())?;

                    for (id, data) in predicate.iter().enumerate() {
                        let data = env.new_string(data)?;
                        subarray.set_element(env, id, &data)?;
                    }

                    array.set_element(env, id, JObject::from(subarray))?;
                }
                log.i(env, "Afrim input suggestions got!")?;

                Ok(array.into())
            } else {
                log.w(env, AFRIM_NOT_CONFIGURED)?;

                Ok(JObject::null())
            }
        })
        .resolve::<ThrowRuntimeExAndDefault>()
        .into_raw()
    }
}
