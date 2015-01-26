# System Manual

## 1 - Installation

### 1.1 - System Requirements

The game will run fine on most new systems manufactured in the last five years. The recommended system requirements are Windows 7® or newer or Mac OS X 10.7 Lion or newer, a Dual-core Intel processor, 4GB of RAM and at least 100MB of free disk space. The minimum system requirements are Windows XP® or newer or Mac OS X 10.5 Leopard, an Intel Atom processor, 2GB of RAM and at least 75MB of free disk space. Most Linux systems should handle the game easily with these or even lesser system specifications.

Since it is written in the Java programming language, it requires Java version 6 or greater to be installed in order to run. Java is available as a free download for Windows, Mac and Linux online at https://www.java.com/en/download/.


### 1.2 - Procedure

The downloaded SS-Java.jar file runs independently and does not require any installation process. Simply double-click on the file to run it. 

When a first level is saved, the game creates a “levels” folder in the same directory as SS-Java.jar, where it stores all the created levels and where it looks first when the user wishes to load a level for editing or gameplay. 

A file named “hs.json” is also created in the same directory when a high score is first obtained and saved by the player. 

A preferences file named “com.asdf.ssjava.preferences” is created in the “.prefs” folder in the user’s home directory, which saves the music & sound volumes, as well as the progress through the game’s levels. (The home directory is usually found at “C:\Users\<UserName>” in Windows Vista or later, at “C:\Documents and Settings\<UserName>\” in Windows XP or older, at “Macintosh HD/Users/<UserName>/” in Mac OS X, and at “/home/<UserName>” on Linux.

Note: To run the project from source, make sure the “ss-java”, “ss-java-android”, and “ss-java-desktop” projects are all present, as they are essential to the functioning of the application. Run the project using the “Main” class in “ss-java-desktop”.


## 2 - System Documentation

A full explanation of the application’s code and various modules in the form of a Javadoc is available with the source attachment of the project. It contains clear and extensive descriptions of each class, method and field used in the project, and points out any intricacies and details that a developer would have to know in order to fix bugs, continue the development of the application, and build upon the application’s core. The documentation is available in HTML and PDF formats.
