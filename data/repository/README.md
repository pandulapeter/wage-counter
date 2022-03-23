# data:repository module
This pure Kotlin module defines the repository interfaces for working with the data. It's meant to be used by other pure Kotlin modules in an abstract way: the actual implementation will only get resolved through DI in the `app` module.

**Depends on**
- `:data:model`

**Exposes API for**
- `:domain`
- `:app` (for DI)