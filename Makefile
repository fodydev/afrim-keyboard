mainDir=app/src/main
jniDir=$(mainDir)/jni

.PHONY: all clean build debug release install ndk format check cbindgen

all: release

cbindgen:
	echo '#include "jni.h"' > ${jniDir}/init.cc
	echo '#include "./afrim_jni/bootstrap.h"' >> ${jniDir}/init.cc
	(cd ${jniDir} && cbindgen afrim_jni | sed 's/^\(\w*\) \(Java\)/JNIEXPORT \1 JNICALL\n\2/g' | sed 's/JNIEnv/&*/g' >> init.cc)
	clang-format -i -style 'file' ${jniDir}/init.cc

format:
	(cd ${jniDir}/afrim_jni && cargo fmt)

check:
	(cd ${jniDir}/afrim_jni && cargo check --quiet)
	(cd ${jniDir}/afrim_jni && cargo clippy --all-features -- -D warnings)
	(cd ${jniDir}/afrim_jni && cargo fmt --check)
	clang-format --dry-run --Werror -style 'file' ${jniDir}/init.cc

clean:
	(cd ${jniDir}/afrim_jni && cargo clean)
	rm -rf build app/build app/.cxx/
	./gradlew clean

build: check
	./gradlew build

release: check
	./gradlew assembleRelease

debug: check
	./gradlew assembleDebug

install: release
	./gradlew installRelease

ndk:
	(cd $(mainDir); ndk-build)
