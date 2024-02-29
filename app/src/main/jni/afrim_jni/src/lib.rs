mod afrim_api;
mod utils;

#[allow(non_snake_case)]
mod android {
    use crate::afrim_api::{Afrim, Singleton};
    use crate::utils::AndroidLogger;
    use jni::{
        objects::{JClass, JObject, JString},
        sys::{jboolean, jintArray, jobjectArray, jstring, jbooleanArray},
        JNIEnv,
    };

    // Singleton
    #[no_mangle]
    pub unsafe extern "C" fn Java_com_afrimkeyboard_inputmethod_afrim_Afrim_nativeInit(
        env: JNIEnv,
        _class: JClass,
    ) {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");
        log.d("Initializing the afrim singleton.");

        Singleton::init_afrim();
        log.i("Afrim singleton initialized!");
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_com_afrimkeyboard_inputmethod_afrim_Afrim_nativeStatus(
        env: JNIEnv,
        _class: JClass,
    ) -> jboolean {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");
        log.d("Getting the afrim singleton status.");

        (*Singleton::get_afrim()).is_some().into()
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_com_afrimkeyboard_inputmethod_afrim_Afrim_nativeUpdateConfig(
        mut env: JNIEnv,
        _class: JClass,
        filename: JString,
    ) -> jboolean {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");

        let filename: String = env.get_string(&filename).unwrap().into();
        log.d(&format!(
            "Updating the afrim singleton with filename={filename}."
        ));

        match Afrim::from_file(&filename) {
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
    pub unsafe extern "C" fn Java_com_afrimkeyboard_inputmethod_afrim_Afrim_nativeDrop(
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
    pub unsafe extern "C" fn Java_com_afrimkeyboard_inputmethod_afrim_Afrim_nativeProcessKey(
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
            let status = afrim.preprocessor.process_key(&key, &state);

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
    pub unsafe extern "C" fn Java_com_afrimkeyboard_inputmethod_afrim_Afrim_nativeCommitText(
        mut env: JNIEnv,
        _class: JClass,
        text: JString,
    ) {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");

        let text = env.get_string(&text).unwrap().into();
        log.d(&format!("Commiting text={text}"));

        let afrim_ptr = Singleton::get_afrim();
        if let Some(afrim) = (*afrim_ptr).as_mut() {
            afrim.preprocessor.commit_text(text);
            log.i("Text committed!");
        } else {
            log.e("Afrim singleton is not yet configured.");
        }
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_com_afrimkeyboard_inputmethod_afrim_Afrim_nativePopStack(
        env: JNIEnv,
        _class: JClass,
    ) -> jstring {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");
        log.d("Getting the afrim command.");

        let afrim_ptr = Singleton::get_afrim();
        if let Some(afrim) = (*afrim_ptr).as_mut() {
            let cmd = afrim.preprocessor.pop_stack();
            let cmd = env.new_string(&cmd).unwrap();
            log.i("Afrim command got!");

            cmd.into_raw()
        } else {
            log.e("Afrim singleton is not yet configured.");

            JObject::null().into_raw()
        }
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_com_afrimkeyboard_inputmethod_afrim_Afrim_nativeClear(
        env: JNIEnv,
        _class: JClass,
    ) {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");
        log.d("Clearing the afrim memory.");

        let afrim_ptr = Singleton::get_afrim();
        if let Some(afrim) = (*afrim_ptr).as_mut() {
            afrim.preprocessor.clear_stack();
            log.i("Afrim memory cleared!");
        } else {
            log.e("Afrim singleton is not yet configured.");
        }
    }

    #[no_mangle]
    pub unsafe extern "C" fn Java_com_afrimkeyboard_inputmethod_afrim_Afrim_nativeGetInput(
        env: JNIEnv,
        _class: JClass,
    ) -> jstring {
        let mut log = AndroidLogger::new(env.unsafe_clone(), "libafrim_jni");
        log.d("Getting of the afrim input text!");

        let afrim_ptr = Singleton::get_afrim();
        if let Some(afrim) = (*afrim_ptr).as_mut() {
            let input = afrim.preprocessor.get_input();
            let input = env.new_string(input).unwrap();
            log.i("Afrim input text got!");

            input.into_raw()
        } else {
            log.e("Afrim singleton is not yet configured.");

            JObject::null().into_raw()
        }
    }

    // Translator
    #[no_mangle]
    pub unsafe extern "C" fn Java_com_afrimkeyboard_inputmethod_afrim_Afrim_nativeTranslate(
        env: JNIEnv,
        _class: JClass,
    ) -> jobjectArray {
        todo!()
    }
}
