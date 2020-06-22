# xdotool-Java
A Java library for accessing [xdotool](https://github.com/jordansissel/xdotool)'s libxdo.

From [xdotool](https://github.com/jordansissel/xdotool) README:

> Fake input from the mouse and keyboard very easily.
    Also supports window manager actions such as moving, activating, and other
    actions on windows.

This uses [JNA](https://github.com/java-native-access/jna) to access the libxdo C functions.
 
## Using xdotool-Java 
### libxdo install
 
 libxdo is not packaged with this library and should be installed
prior to using this library. Installing from the target linux distribution 
is the easiest. Most of the prominent distributions have xdotool packages. For
 example using Debian: `apt-get install libxdo-dev`.
  
  Otherwise, libxdo can be built from [source](https://github.com/jordansissel/xdotool).

### Library import

Gradle:
```
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.kingpulse:xdotool-java:1.0")
    }
```

Maven:
```
   <dependency>
     <groupId>io.github.kingpulse</groupId>
     <artifactId>xdotool-java</artifactId>
     <version>1.0</version>
   </dependency>
```

Each released version is also published to github packages.
