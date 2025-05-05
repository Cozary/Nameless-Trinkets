# Ethereal Wings

## How it works:
The **Ethereal Wings** trinket grants the following effects:

- When equipped, the player gains the ability to fly, and their flying speed is adjusted.
- When unequipped, the player loses their flying abilities and the flying speed returns to the default value.

## Configuration:
- **Is Enable**: Determines whether the trinket's effect is active or not.  
- **Flying Speed**: Determines the speed at which the player flies.

## Notes:
- The player's flying speed is set back to the default value of `0.05F` when the trinket is unequipped.


??? warning "Incompatibilities"

    The trinket may stop working or malfunction when used with other mods that modify the flight or use it.
    This is because they take full control over their flight cancellation or use.

    *For example: if a mod indicates that flight is canceled while an item of theirs is unequipped, even if the Ethereal Wings indicate that the player can fly, the other item will be canceling it. I mention this example because I've encountered this before.*