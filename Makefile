mainDir=app/src/main
jniDir=$(mainDir)/jni

.PHONY: help all clean build debug release install ndk format check cbindgen

help: ## Shows this help.
	awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m\033[0m\n"} /^[$$()% a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)

all: clean cbindgen format check release ## Compiles the project from scratch.

cbindgen: ## Generates the c binding header of the afrim_jni library.
	echo '#include "jni.h"' > ${jniDir}/init.cc
	echo '#include "./afrim_jni/bootstrap.h"' >> ${jniDir}/init.cc
	( \
		cd ${jniDir} && cbindgen afrim_jni | \
		sed 's/^\(\w*\) \(Java\)/JNIEXPORT \1 JNICALL\n\2/g' | \
		sed 's/JNIEnv/&*/g' | \
		sed 's/)/) {\n\/\/ For more details about the implementation, consult the rust code.\n}/g' \
		>> init.cc \
	)
	clang-format -i -style 'file' ${jniDir}/init.cc

format: ## Formats the code.
	(cd ${jniDir}/afrim_jni && cargo fmt)

check: ## Checks the project.
	(cd ${jniDir}/afrim_jni && cargo check --quiet)
	(cd ${jniDir}/afrim_jni && cargo clippy --all-features -- -D warnings)
	(cd ${jniDir}/afrim_jni && cargo fmt --check)
	clang-format --dry-run --Werror -style 'file' ${jniDir}/init.cc

clean: ## Clean the project.
	(cd ${jniDir}/afrim_jni && cargo clean)
	rm -rf build app/build app/.cxx/
	./gradlew clean

build: check ## Builds the project.
	./gradlew build

release: check ## Compiles in release mode.
	./gradlew assembleRelease

debug: check ## Compiles in debug mode.
	./gradlew assembleDebug

install: release ## Installs the application in a connected device.
	./gradlew installRelease

ndk: ## Build the project in using NDK.
	(cd $(mainDir); ndk-build)
