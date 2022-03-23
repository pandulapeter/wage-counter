# domain module
This pure Kotlin module contains all the use cases that define the business logic of the application.

It encapsulates the implementation details of the different repositories and only exposes their results and the domain models for the various feature modules.

For the sake of reducing code duplication, the domain models in fact come directly from the `data:model` module.

**Depends on**
- `:data:model`
- `:data:repository`

**Exposes API for**
- `:presentation:main`
- `:app` (for DI)