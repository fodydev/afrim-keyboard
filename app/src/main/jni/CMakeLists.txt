cmake_minimum_required(VERSION 3.22)

project(afrim_jni)

include(FetchContent)

FetchContent_Declare(
        Corrosion
        GIT_REPOSITORY https://github.com/corrosion-rs/corrosion.git
        GIT_TAG v0.4.7 # Optionally specify a commit hash, version tag or branch here
)
# Set any global configuration variables such as `Rust_TOOLCHAIN` before this line!
FetchContent_MakeAvailable(Corrosion)

# Import targets defined in a package or workspace manifest `Cargo.toml` file
corrosion_import_crate(
		MANIFEST_PATH afrim_jni/Cargo.toml
		# FEATURES inhibit
		# TODO: Removed it.
		# Follows the make profile by default.
		# I enable it for test purpose
		PROFILE release
)

# In this example the the `Cargo.toml` file passed to `corrosion_import_crate` is assumed to have
# defined a static (`staticlib`) or shared (`cdylib`) rust library with the name "rust-lib".
# A target with the same name is now available in CMake and you can use it to link the rust library into
# your C/C++ CMake target(s).
add_library(init
	SHARED
	init.cc
)
target_link_libraries(init PUBLIC afrim_jni)
