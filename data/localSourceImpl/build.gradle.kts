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
    implementation(project(":data:model"))
    api(project(":data:localSource"))
}