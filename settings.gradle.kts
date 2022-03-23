// Module definitions
include(
    ":app",
    ":data:model",
    ":data:localSource",
    ":data:localSourceImpl",
    ":data:repository",
    ":data:repositoryImpl",
    ":domain",
    ":presentation:debugMenu",
    ":presentation:main"
)

// Project-level configuration
rootProject.name = "Wage Counter"
enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}