# Changelog 1.21.1

## Changed

- Refactor [Dragon's Eye] and [Rage Mind] to use unique per-player scoreboard teams, ensure cleanup on unequip, and hide glowing effect particles. #45

## Removed

- Removed all traces of lang tooltips that no longer exist. #43

### Fixed

- Reload config and apply RecipeGate on datapack sync across all platforms
- Fixed potential crash in `TrinketConfigs` by adding a null check for `relativeParent` during backup path generation #46
- Fixed a critical issue where the [Rage Mind] and [Dragon's Eye] could cause server crashes by filling up the scoreboard data file over time.
- [ExperienceBattery] Fixed a crash where entity was null
- Fixed a critical bug on NeoForge where several damage-modifying trinkets would cause incoming damage to incorrectly bypass armor and enchantments. #35
