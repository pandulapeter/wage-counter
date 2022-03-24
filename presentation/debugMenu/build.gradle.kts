plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    val targetSdkVersion = System.getProperty("TARGET_SDK_VERSION").toInt()
    compileSdk = targetSdkVersion
    defaultConfig {
        minSdk = System.getProperty("MIN_SDK_VERSION").toInt()
        targetSdk = targetSdkVersion
    }
}

dependencies {
    debugImplementation(libs.beagle.drawer)
    debugImplementation(libs.androidx.lifecycle)
    debugImplementation(project(":domain"))
    implementation(libs.koin.core)
    implementation(project(":data:model"))
}