# presentation:debugMenu module
This Android feature module defines the presentation layer of the "debugMenu" feature. It does not depend on any other feature modules, but all other feature modules depend on it.

The module exposes the `DebugMenu` interface which has different implementations based on the build type (in `release` builds the implementation does nothing: none of the logic related to the debug menu is compiled into production binaries).

**Depends on**
- `:data:model` (just to simplify the debugging API)

**Exposes API for**
- `:app`
- `:presentation:main`