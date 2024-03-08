mod afrim_api;
mod utils;

#[allow(non_snake_case)]
mod android {
    use crate::afrim_api::{Afrim, Singleton};
    use crate::utils::AndroidLogger;
    use jni::{
        objects::{JClass, JObject, JString},
        sys::{jboolean, jbooleanArray, jintArray, jobjectArray, jstring},
        JNIEnv,
    };

    // Singleton
    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeInit(
        env: JNIEnv,
        _class: JClass,
    ) {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");
        log.d("Initializing the afrim singleton.");

        Singleton::init_afrim();
        log.i("Afrim singleton initialized!");
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeIsInit(
        env: JNIEnv,
        _class: JClass,
    ) -> jboolean {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");
        log.d("Checking the presence of the afrim singleton.");

        (*Singleton::get_afrim()).is_some().into()
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeUpdateConfig(
        mut env: JNIEnv,
        _class: JClass,
        config_file: JString,
    ) -> jboolean {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");

        let config_file: String = env.get_string(&config_file).unwrap().into();
        log.d(&format!(
            "Updating the afrim singleton using config_file={config_file}."
        ));

        match Afrim::from_config(&config_file) {
            Ok(new_afrim) => {
                Singleton::update_afrim(new_afrim);
                log.i("Afrim singleton updated!");

                1
            }
            Err(err) => {
                log.e(&format!(
                    "Error while the updating of the afrim singleton: {err}",
                ));

                0
            }
        }
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeDrop(
        env: JNIEnv,
        _class: JClass,
    ) {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");
        log.d("Dropping the afrim singleton.");

        Singleton::drop_afrim();
        log.i("Afrim singleton dropped!");
    }

    // Preprocessor
    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeProcessKey(
        mut env: JNIEnv,
        _class: JClass,
        key: JString,
        state: JString,
    ) -> jbooleanArray {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");

        let key: String = env.get_string(&key).unwrap().into();
        let state: String = env.get_string(&state).unwrap().into();
        log.d(&format!("Processing key={key} state={state}."));

        let afrim_ptr = Singleton::get_afrim();
        if let Some(afrim) = (*afrim_ptr).as_mut() {
            let status = afrim.process_key(&key, &state);

            match status {
                Ok((changed, committed)) => {
                    let status = env.new_boolean_array(2).unwrap();
                    env.set_boolean_array_region(&status, 0, &[changed.into(), committed.into()])
                        .unwrap();
                    log.i("Key processed!");

                    return status.into_raw();
                }
                Err(err) => {
                    log.e(format!("Error while key processing: {err}"));
                }
            }
        } else {
            log.w("Afrim singleton is not yet configured.");
        };

        JObject::null().into_raw()
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeCommitText(
        mut env: JNIEnv,
        _class: JClass,
        text: JString,
    ) {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");

        let text = env.get_string(&text).unwrap().into();
        log.d(&format!("Commiting text={text}"));

        let afrim_ptr = Singleton::get_afrim();
        if let Some(afrim) = (*afrim_ptr).as_mut() {
            afrim.commit_text(text);
            log.i("Text committed!");
        } else {
            log.w("Afrim singleton is not yet configured.");
        }
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeNextCommand(
        env: JNIEnv,
        _class: JClass,
    ) -> jstring {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");
        log.d("Getting the afrim command.");

        let afrim_ptr = Singleton::get_afrim();
        if let Some(afrim) = (*afrim_ptr).as_mut() {
            let cmd = afrim.next_command();
            let cmd = env.new_string(&cmd).unwrap();
            log.i("Afrim command got!");

            cmd.into_raw()
        } else {
            log.w("Afrim singleton is not yet configured.");

            JObject::null().into_raw()
        }
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeClear(
        env: JNIEnv,
        _class: JClass,
    ) {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");
        log.d("Clearing the afrim memory.");

        let afrim_ptr = Singleton::get_afrim();
        if let Some(afrim) = (*afrim_ptr).as_mut() {
            afrim.clear();
            log.i("Afrim memory cleared!");
        } else {
            log.w("Afrim singleton is not yet configured.");
        }
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeGetInput(
        env: JNIEnv,
        _class: JClass,
    ) -> jstring {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");
        log.d("Getting of the afrim input text.");

        let afrim_ptr = Singleton::get_afrim();
        if let Some(afrim) = (*afrim_ptr).as_mut() {
            let input = afrim.get_input();
            let input = env.new_string(input).unwrap();
            log.i("Afrim input text got!");

            input.into_raw()
        } else {
            log.w("Afrim singleton is not yet configured.");

            JObject::null().into_raw()
        }
    }

    // Translator
    #[no_mangle]
    pub unsafe extern "C" fn Java_cm_pythonbrad_afrim_core_Afrim_nativeTranslate(
        mut env: JNIEnv,
        _class: JClass,
    ) -> jobjectArray {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");
        log.d("Getting of the afrim suggestions.");

        let afrim_ptr = Singleton::get_afrim();
        if let Some(afrim) = (*afrim_ptr).as_mut() {
            let input = afrim.get_input();
            let predicates = afrim.translate_text(&input);

            let array_class = env.find_class("java/lang/Object").unwrap();
            let subarray_class = env.find_class("java/lang/String").unwrap();

            let length = predicates.len() as i32;
            let array = env
                .new_object_array(length, &array_class, JObject::null())
                .unwrap();

            predicates.iter().enumerate().for_each(|(id, predicate)| {
                let length = predicate.len() as i32;
                let subarray = env
                    .new_object_array(length, &subarray_class, &JObject::null())
                    .unwrap();

                predicate.iter().enumerate().for_each(|(id, data)| {
                    let data = env.new_string(data).unwrap();

                    env.set_object_array_element(&subarray, id as i32, data)
                        .unwrap();
                });

                env.set_object_array_element(&array, id as i32, subarray)
                    .unwrap();
            });
            log.i("Afrim input suggestions got!");

            array.into_raw()
        } else {
            log.w("Afrim singleton is not yet configured.");

            JObject::null().into_raw()
        }
    }
}
