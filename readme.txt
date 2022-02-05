==================================================================
====================== Family Tree Maker =========================
==================================================================
Author: Doyoung Oh
Files included:
- Address.java 
- FamilyMember.java
- FamilyTree.java
- GUI.java
- Main.java
- UnitTest.java (used for unit testing only)

==================================================================
Assumptions
==================================================================

-   The user will utilise the family tree maker through the use of 
    mouse and keyboard manipulation

-   The family tree is modeled after conventional families 
    (mother-father-children)

==================================================================
Requirements & Specifications
==================================================================

To run the program the machine must be installed with JDK 8 and over

==================================================================
User Guide
==================================================================

### Command line

From the command prompt, change the directory location using ‘cd’ 
command to go to the directory containing the .jar files.

If you are running under Java SE 8:
java -jar family_tree_maker.jar

If you are running over Java SE 9 and over:
java -jar --module-path ${your_javafx_module_path} --add-modules 
javafx.controls,javafx.fxml family_tree_maker.jar


### Using IDE

Please place the necessary files in the directory before executing 
the program:

- src: The source codes, inside the source file, there is a 
	FamilyTreeMakerStyle.css file that styles the GUI components 
- family: The FamilyTree objects are serialised into '.ftree' files 
	and stored here
- log: It is the location where all the program log files are stored
- asset: It is the folder that holds an image file used for the GUI

