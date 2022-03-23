# data:repositoryImpl module
This Android module defines the repository interfaces for working with the data. It should never be used directly, as this implementation will only get resolved through DI in the `app` module.

**Depends on**
- `:data:model`
- `:data:localSource`
- `:data:repository`

**Exposes API for**
- `:app` (for DI)