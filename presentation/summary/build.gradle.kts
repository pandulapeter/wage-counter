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
    buildFeatures.compose = true
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions.jvmTarget = "1.8"
    composeOptions.kotlinCompilerExtensionVersion = libs.versions.androidx.compose.get()
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":presentation:shared"))
    implementation(project(":presentation:debugMenu"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.tooling)
    implementation(libs.koin.compose)
}