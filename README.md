Atari 2600 Warlords Clone in Java. Design project for COMPSYS 302 Digital Systems Design 1 Semester 1 2017 at the Univesity of Auckland.

A gradle wrapper has been included with the source code and can be used to download the apporpriate version of graddle and use it.

Before using the graddle wrapper on *nix based systems, please make the 'gradlew' file exectuable by running 
```
#!shell

chmod +x gradlew
```


Usage Instructions for gradle build system:

```
#!shell

gradlew clean
```
 to clean your working directory

```
#!shell

gradlew copyRes
```
 to copy the graphics necessary for the game into the correct folder structure

```
#!shell

gradlew run
```
 to build & run the game

NB: the copyRes task must be run before the game GUI can be launched.