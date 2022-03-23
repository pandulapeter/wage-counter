# app module
This Android module is the main entry point for the entire application.

It is only responsible for the DI (resolving the dependency graph) and the Application class. All other logic should be defined in other modules.

The app module uses the API from the feature modules, but to take care of DI it also has to know about every other module that has injectable classes. This is the reason why it should not contain too much logic: having access to all layers of the architecture gives too many opportunities to break the rules.

**Depends on**
- `:data:localSourceImpl` (transitively also on `:data:localSource`)
- `:data:repositoryImpl` (transitively also on `:data:repository`)
- `:domain` (transitively also on `:data:model`)
- `:presentation:debugMenu`
- `:presentation:main`