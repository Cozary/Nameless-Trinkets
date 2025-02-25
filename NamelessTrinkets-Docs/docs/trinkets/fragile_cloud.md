# Fragile Cloud

## How it works:
The **Fragile Cloud** trinket grants the following effect:

- Modifies the player's gravity, allowing them to fall more slowly.
- The player does not take fall damage.

## Configuration:
- **Is Enable**: Determines whether the trinket's effect is active or not.  

## Notes:
- The **Moon Stone** disables the **Fragile Cloud** effect entirely, preventing any gravity modification or cloud particles.
- This effect is active when the player is not in water, not on the ground, and not flying.
- The attribute modifier is applied if the player is falling at a significant speed (`y < -0.3`).
- There is a particle effect that activates when you fall.