plugins {
    id("kotlin")
}

dependencies {
    api(project(":data:model"))
    implementation(project(":data:repository"))
}