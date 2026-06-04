# Changelog 1.21.1

### Changed

- Refactor [Dragon's Eye] and [Rage Mind] to use unique per-player scoreboard teams, ensure cleanup on unequip, and hide glowing effect particles. #45
- [DyingStar] Substantially buffed upgrade increments from consumed trinkets.
- [Woundbearer] Added max damage limit configuration and a continuous decay over time that accelerates near the maximum cap to prevent infinite damage stacking.

### Removed

- Removed all traces of lang tooltips that no longer exist. #43

### Fixed

- Reload config and apply RecipeGate on datapack sync across all platforms
- Fixed potential crash in `TrinketConfigs` by adding a null check for `relativeParent` during backup path generation #46
- Fixed a critical issue where the [RageMind] and [Dragon'sEye] could cause server crashes by filling up the scoreboard data file over time.
- [ExperienceBattery] Fixed a crash where entity was null
- Fixed a critical bug on NeoForge where several damage-modifying trinkets would cause incoming damage to incorrectly bypass armor and enchantments. #35
- Fix [IceCube] applying slowness to player instead of target entity.
- [TrinketUtils] Added defensive null check for player in getEquippedTrinket to prevent NPE crashes.
- [Woundbearer] Fixed a bug on NeoForge where the damage counter would increment constantly while standing in lava instead of only on actual damage ticks.
- [SpeedForce/TearOfTheSea] Fixed a bug where players could bypass the server's disable config due to missing client-side checks in the tick methods.
- [FracturedNullstone] Fixed inverted magic damage reduction formula to correctly apply the configured percentage as a reduction rather than a multiplier of damage taken.