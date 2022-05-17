// Module definitions
include(
    // Entry point
    ":app",

    // Data layer
    ":data:model",
    ":data:localSource",
    ":data:localSourceImpl",
    ":data:repository",
    ":data:repositoryImpl",

    // Domain layer
    ":domain",

    // Presentation layer
    ":presentation:main",
    ":presentation:summary",
    ":presentation:startTime",
    ":presentation:dayLength",
    ":presentation:hourlyWage",
    ":presentation:shared",
    ":presentation:debugMenu"
)

// Project-level configuration
rootProject.name = "Wage Counter"
enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}