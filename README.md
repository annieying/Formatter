# Formatter

Code formatter that formats a code snippet according to Check Style profiles.

Makes use of Eclipse (running headless).


## Installation

(Tested on Eclipse 4.5 for RCP )

Create a workspace someplace outside this folder.

In eclipse, go to project / import / existing projects into workspace

Select as root directory the *parent* directory for the folder containing this project.

Import it.

Eclipse will then compile it. If there are any errors check whether you have the right JRE installed.

Once it has finished building, select the project in the Package
Explorer, then File > Export > Plug-in Development (you need an RCP
version of Eclipse to see this menu) > Deployable plug-ins and fragments.

In the target folder you choose you should have a plugins/ folder with a Formatter_<version>.jar

(There is also an ant build.xml file generated from Eclipse, it is untested, if you want to use it
you need to set the path to an installed eclipse at the top of the file.)

Continue the installation instructions in https://github.com/annieying/FormatterEclipseServer
