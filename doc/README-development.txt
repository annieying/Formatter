--- Setting up Headless Eclipse for testing---

Adapted from

	http://www.sable.mcgill.ca/ppa/tut_4.html

Write the "main" by implementing the IApplication interface

	Add the extension org.eclipse.core.runtime.applications (in the Extensions view)
	
	ID is the extension id (needed to invoke the application)
	
	Add "run" (right click application --> New --> run)
	The class specified is the "main" (click "class" to create a new class or verify)


To test it in Eclipse:
	
	Run --> Run Configurations 

	Program to Run --> Run an application --> <plugin-id>.<extension-id>


To run it command-line:
	
	Pre-install eclipse on $ECLIPSE_DIR: /diskless/local/annie/workspaces/20140228-summarizer/headless-eclipse/
	
	Export --> Deployable plugins and fragments

	Directory: $ECLIPSE_DIR/eclipse
	
	cd $ECLIPSE_DIR/eclipse

	Copy res directory to here
	
	./eclipse -nosplash -application Formatter.id1 # <plugin-id>.<extension-id> 


References:

	http://blogs.operationaldynamics.com/andrew/software/java-gnome/eclipse-code-format-from-command-line


--- Generating formatting file (check-style.xml) ---
 
Go to runtime Eclipse -> Window -> Preference -> Java -> CodeStyle 
Import /change profile

References:
https://github.com/sevntu-checkstyle/sevntu.checkstyle/wiki/Java-code-Formater-and-Checkstyle-configuration-for-development
http://stackoverflow.com/questions/984778/how-to-generate-an-eclipse-formatter-configuration-from-a-checkstyle-configurati

Programmatically:
http://denizstij.blogspot.ca/2009/10/amending-code-style-templates.html

