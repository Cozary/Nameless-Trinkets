# Changelog 1.21.5

## Changed

- Refactor [Dragon's Eye] and [Rage Mind] to use unique per-player scoreboard teams, ensure cleanup on unequip, and hide glowing effect particles. #45

## Removed

- [Loot Tables] Removed trinkets from multiple loot tables to make them rarer.
- [Fertilizer] Improved logic to prioritize crops/saplings and added safety checks to prevent underwater growth near the surface. (@JahsonGA)
- Removed all traces of lang tooltips that no longer exist. #43

### Fixed

- [Four Leaf Clover] Fixed an issue where player-kill loot (e.g. Blaze Rods) wouldn't drop and extra rolls were not randomized.
- [Woundbearer] Fixed an issue where damage accumulation was not being saved correctly.
- [Sigil of Baphomet] Fixed an issue where kill counts were not being tracked.
- [Rage Mind] Fixed an issue where the revenge target was not being stored.
- [Electric Paddle] Fixed an issue where the effect was applied when not equipped and deactivated when equipped.
- [Loot Tables] Fixed a critical issue on NeoForge where loot injection would stop after the first match.
- Reload config and apply RecipeGate on datapack sync across all platforms. #49
- Fixed potential crash in `TrinketConfigs` by adding a null check for `relativeParent` during backup path generation. #46
- Fixed a critical issue where the [Rage Mind] and [Dragon's Eye] could cause server crashes by filling up the scoreboard data file over time.
- [ExperienceBattery] Fixed a crash where entity was null. #48
- Fixed a critical bug on NeoForge where several damage-modifying trinkets would cause incoming damage to incorrectly bypass armor and enchantments. #35
