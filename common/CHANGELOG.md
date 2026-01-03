# Changelog

## Changed

- Base trinket slot count is now 2 by default.
- Added a global trinket loot multiplier config (`globalLootMultiplier`) that scales all trinket drop chances proportionally.
- Added a config option to enable or disable trinket crafting (`enableTrinketCrafting`, default: false).
- Trinket crafting recipes are now gated at runtime and can be toggled without changing recipe IDs or data packs.
- [Fertilizer] Growth logic updated to properly target crops, saplings, and tagged growables instead of only grass and seagrass.
- [Fertilizer] Trigger rate adjusted to once every 10 seconds to reduce excessive updates and improve server performance.
- [Fertilizer] Added server-side logging for growth attempts to aid debugging and balance testing.

## Removed

- [Config] `startingSlotQuantity` is no longer used.
- [Config] `slotProbability` and `trinketSlots` have been removed, as they are no longer relevant with Mysterious Trinket functionality disabled.
- [Mysterious Trinket] No longer grants additional trinket slots when used.

## Fixed

- [Mysterious Trinket] Did not correctly add the expected number of trinket slots.
- [Creeper Sense] Infinite explosion loop when there was no space above the player, preventing the player from exiting sneak.
- [Fertilizer] Uncontrolled seagrass growth and water flooding behavior.
- [Fertilizer] Client-only execution causing effects to appear in singleplayer but not on dedicated servers.

## Technical

- Recipe gating now operates by recipe ID path filtering (`data/nameless_trinkets/recipe/*`) rather than result-item inspection, ensuring ID safety and mod compatibility.
- Configuration values are clamped and validated to prevent invalid loot chances.
- Changes are compatible with Fabric, Forge, and NeoForge (1.21.4) multi-loader builds.

## Credits

- Implemented and authored by: JahsonGA
- Original mod by: Nameless Trinkets contributors, Cozary