# data:localSourceImpl module
This Android module defines the local source implementations for accessing the data. It should never be used directly, as this implementation will only get resolved through DI in the `app` module.

**Depends on**
- `:data:model`
- `:data:localSource`

**Exposes API for**
- `:app` (for DI)