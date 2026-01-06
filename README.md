# MeeCreeps RELOADED
**[McJty's](https://github.com/McJty) MeeCreeps!** .. but with added features:
 * Open Destination Wheel while flying! No need to target a block anymore!
 * Custom Coordinates! Tune the portal gun to custom XYZ coords on-the-go. Screw having to visit places first!

## Destination Wheel Airborne
**Shift-Right-Clicking air** while flying brings up the destination wheel.  

**Left-Clicking** a destination quadrangle will save a **horizontally** structured portal, in relation to the direction player is facing.  
**Right-Clicking** a destination quadrangle will save a **vertically** structured portal.  
![HorizontalAndVerticalMidAirPortals](https://i.imgur.com/dNiyp8r.png)  
![DestinationWheel](https://i.imgur.com/a4TEKhX.png)  

## Custom Coordinates
Pressing **INSERT** while highlighting a destination quadrant will bring up custom coordinates input menu.  
![CustomCoordinates](https://i.imgur.com/NKZeftT.png)

Input coordinates can include decimal points (3.14), although they will be rounded to the nearest whole number.  
It is very important to **separate each number**. The encouraged way is by using a semicolon (`;`)  
Input coordinates can also specify the direction a portal will face. These can be Up, Down, West, East, North South. They are specified by their respective first **CAPITAL** letters.  

| Letter | Direction |
|:------:|:---------:|
|    U   |     Up    |
|    D   |    Down   |
|    W   |    West   |
|    E   |    East   |
|    N   |   North   |
|    S   |   South   |

### Examples
**X:** 50.54 **Y:** 32 **Z:** -251.3 -> `50.54;32;-251`  
**X:** 30.301 **Y:** 5 **Z:** -30 **Direction:** East -> `30.301;5;-30;E`

![ExampleCoordinates](https://i.imgur.com/zQgzAMB.png)

## More Planned features:
 * Portal Gun self-destruct mechanism
 * Dimension specification for custom coordinates

## Disclaimer
This is literally my first time ever writing something in Java, no kidding.  
I don't know anything about the language and I'm relying only on my intuition, googel and trial-and-error to glue things together. The code is most probably horseshit, which brings me to my next point:  
There won't be any pull requests to the official repo, simply because the quality of my code is nowhere near to [@McJty's](https://github.com/McJty) code. I don't code in Java and my code doesn't represent the quality of the original mod. I have no idea what issues can my code cause, especially in combination with other mods, because I simply have very faint understanding of the whole thing and absolutely no understanding of Minecraft modding practices. If McJty wants to implement these features, he will most definitely do better himself, especially since this shit was written within a day. Please don't report issues caused by this "modified mod" to McJty. If you find an issue, please open it here, although I have to say, I probably won't be able to do much with even trivial problems. That doesn't mean I won't try!