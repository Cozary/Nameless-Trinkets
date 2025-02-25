# Creeper Sense

## How it works:
The **Creeper Sense** trinket grants the following effect:

-It triggers an explosion when the player crouches.

## Configuration:
- **Is Enable**: Determines whether the trinket's effect is active or not.  
- **Explosion Level**: The strength of the explosion.
  
## Notes:
- The explosion only triggers while the player is crouching, and after it happens, the player's crouch state will be reset (i.e., the player will no longer be crouching).
- The explosion will not interact with the environment or break blocks, as the `ExplosionInteraction` is set to `NONE`.
- Explosion damage affects the player.