plugins {
    id("kotlin")
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    api(project(":data:model"))
    implementation(project(":data:repository"))
    implementation(libs.koin.core)

    // Test dependencies
    testImplementation(libs.junit)
}