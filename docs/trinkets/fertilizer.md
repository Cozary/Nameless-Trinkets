# Fertilizer

## How it works:
The **Fertilizer** trinket grants the following effect:

- Acts like `Bonemeal` on a large area around the player, affecting both land and underwater plants.

## Configuration:
- **Is Enable**: Determines whether the trinket's effect is active or not.  

## Notes:
- Growth particles are spawned to indicate successful activation.
- The logic now prioritizes applying the effect to crops and saplings first.
- It includes safety checks to prevent underwater growth too close to the surface.
- It has a hidden 35% chance of activating again if it fails the first time.
