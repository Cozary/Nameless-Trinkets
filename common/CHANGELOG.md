# Changelog 1.21.5

### Added

- [Config] Added `globalLootMultiplier` to easily adjust trinket drop rates globally. (@JahsonGA)
- [Config] Added `enableTrinketCrafting` option (currently enabled by default). (@JahsonGA)
- [Recipes] Added a configuration option to enable or disable trinket crafting recipes. (@JahsonGA)

### Changed

- [Loot Tables] Removed trinkets from multiple loot tables to make them rarer.
- [Fertilizer] Improved logic to prioritize crops/saplings and added safety checks to prevent underwater growth near the surface. (@JahsonGA)

### Fixed

- [Four Leaf Clover] Fixed an issue where player-kill loot (e.g. Blaze Rods) wouldn't drop and extra rolls were not randomized.
- [Woundbearer] Fixed an issue where damage accumulation was not being saved correctly.
- [Sigil of Baphomet] Fixed an issue where kill counts were not being tracked.
- [Rage Mind] Fixed an issue where the revenge target was not being stored.
- [Electric Paddle] Fixed an issue where the effect was applied when not equipped and deactivated when equipped.
- [Loot Tables] Fixed a critical issue on NeoForge where loot injection would stop after the first match.
