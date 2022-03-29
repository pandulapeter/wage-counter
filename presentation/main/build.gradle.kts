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
    implementation(project(":presentation:summary"))
    implementation(project(":presentation:startTime"))
    implementation(project(":presentation:dayLength"))
    implementation(project(":presentation:hourlyWage"))
    implementation(project(":presentation:debugMenu"))
    api(project(":presentation:shared"))

    // Compatibility / UI libraries
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)
    implementation(libs.androidx.core.splash)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.tooling)

    // Dependency injection
    implementation(libs.koin.compose)
}