# xdotool-Java
A Java library for accessing [xdotool](https://github.com/jordansissel/xdotool)'s libxdo.

From [xdotool](https://github.com/jordansissel/xdotool) README:

> Fake input from the mouse and keyboard very easily.
    Also supports window manager actions such as moving, activating, and other
    actions on windows.

This uses [JNA](https://github.com/java-native-access/jna) to access the libxdo C functions.
 
 ## Installing libxdo
 
 Installing from the target linux distribution is the easiest. Most of the prominent distributions
  have xdotool packages. For example using Debian: `apt-get install libxdo-dev`.
  
  Otherwise, libxdo can be built from [source](https://github.com/jordansissel/xdotool).
 
