# Wage Counter
This simple application demonstrates a modular, clean architecture approach to Android development.

See how every submodule has a readme file that clarifies its purpose as well as its dependencies.

It also serves as my playground for experimenting with Jetpack Compose, the Gradle Kotlin DSL, Gradle version catalogs and unit testing.

- `:app` - Android module, the main entry point of the application.
- `:data` - Folder containing modules that handle the data layer.
- `:domain` - Pure Kotlin module that handles the domain layer.
- `:presentation` - Folder containing feature modules that handle the presentation layer.
- `:gradle` - Folder containing the version catalog and the Gradle wrapper.

Work in progress. Current priorities:
- Finalize the UI
- Add @Preview functions for all UI blocks
- Reduce code duplication in `gradle.kts` files
- Add more tests