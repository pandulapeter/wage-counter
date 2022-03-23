# data:model module
This pure Kotlin module only contains simple data classes that will be exposed for other `data` modules, as well as the `domain` module.

For the sake of simplicity and for reducing duplicated code, `domain` will expose these models to the UI layer as an API, so these data models also serve as domain models.

**Exposes API for**
- `:domain`