# Changelog

### Added

- [Rage Mind] glowing.
- [Woundbearer] clamp to limit max value.
- [Trinket Bundle] recipe.
- [Dying Star] config.
- [Resonant Heart] config for damage.
- [Creeper Sense] cooldown.
- New config common config

### Changed

- LivingDamageEvent.Pre replaced by LivingIncomingDamageEvent
- The events have been abstracted to Common. This may cause problems.
- The loot table for [God's Crown] and [Dying Star] is now generated, but with a value of 0.
- [Four Leaf Clover] its description is now somewhat clearer.

### Removed

- **Forge Config Api** dependency.
- Old common config.

### Fixed

- [Fertilizer] re-added effectIntervalInTicks to its corresponding function.