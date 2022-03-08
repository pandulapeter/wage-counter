// Important release constants extracted for convenience
"VERSION_CODE" set 1
"VERSION_NAME" set "0.0.1"
"KEY_ALIAS" set "androiddebugkey"
"KEY_PASSWORD" set "android"
"STORE_FILE" set "internal.keystore"
"STORE_PASSWORD" set "android"
"TARGET_SDK_VERSION" set 32
"MIN_SDK_VERSION" set 29

// Used for saving the constants from above as system properties, so that all modules can access them
infix fun String.set(value: Any) = System.setProperty(this, value.toString())

// Project-level configuration
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.kotlin.gradle)
    }
}