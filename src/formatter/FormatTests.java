package formatter;

import java.io.File;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.IndentManipulation;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

public class FormatTests {

	public static void format() {
		try {
			testFormatterWholeCU();
		} catch (Exception e) {
			System.out.println();
		}
	}

	public static void testFormatterWholeCU() throws MalformedTreeException,
			BadLocationException {
		String code = "package foo;\n\n\nimport abc;\n\n\n"
				+ "public class Temp {\n"
				+ "public static void main(String[] args) {\n"
				+ "mBuilder.setContentText(\"Download complete\")"
				+ ".setProgress(0,0,false);\n" + "}\n" + "}";

		String codeStyleFile = "/diskless/local/annie/workspaces/20140228-summarizer/Formatter/res/eclipse-code-style-files/profiles.xml";
		String formattedCode = EclipseCodeStyleFormatter.format(code, new File(
				codeStyleFile));
		System.out.println(formattedCode);
	}

	public static void testFormatOneLine() throws MalformedTreeException,
			BadLocationException {

		String str = "    mBuilder.setContentText(\"Download complete\")"
				+ ".setProgress(0,0,false);\n";
		CodeFormatter formatter = ToolFactory.createCodeFormatter(JavaCore
				.getOptions());

		int indentationLevel = 1;
		TextEdit edit = formatter.format(CodeFormatter.K_UNKNOWN, str, 0,
				str.length(), indentationLevel, null);

		IDocument document = new Document(str);
		edit.apply(document);

		String formattedString = document.get();

		System.out.println(formattedString);
	}

	public static void testFormatter3() throws MalformedTreeException,
			BadLocationException {

		String str = "package foo;\n\n\nimport abc;\n\n\n"
				+ "public class Temp {\n"
				+ "public static void main(String[] args) {\n"
				+ "mBuilder.setContentText(\"Download complete\")"
				+ ".setProgress(0,0,false);\n" + "}\n" + "}";
		Map options = JavaCore.getOptions();

		int tabWidth = IndentManipulation.getTabWidth(options);
		int indentWidth = IndentManipulation.getIndentWidth(options);
		int indent = 1;

		CodeFormatter formatter = ToolFactory.createCodeFormatter(JavaCore
				.getOptions());
		int indentationLevel = 1;
		String indentString = formatter
				.createIndentationString(indentationLevel);
		ReplaceEdit[] edits = IndentManipulation.getChangeIndentEdits(str, 0,
				tabWidth, indentWidth, indentString);
		IDocument document = new Document(str);

		MultiTextEdit edit = new MultiTextEdit();
		edit.addChild(new InsertEdit(0, indentString));
		edit.addChildren(edits);
		edit.apply(document);
		String oneTabApplied = document.get();

		System.out.println(oneTabApplied);
	}

	public static void ast() {
		String str = "package foo;\n\n\nimport abc;\n\n\n"
				+ "public class Temp {\n"
				+ "public static void main(String[] args) {\n"
				+ "mBuilder.setContentText(\"Download complete\")"
				+ ".setProgress(0,0,false);\n" + "}\n" + "}";
		char[] source = str.toCharArray();
		ASTParser parser = ASTParser.newParser(AST.JLS3); // handles JDK 1.0,
															// 1.1, 1.2, 1.3,
															// 1.4, 1.5, 1.6
		parser.setSource(source);
		// In order to parse 1.5 code, some compiler options need to be set to
		// 1.5
		Map options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_5, options);
		parser.setCompilerOptions(options);
		CompilationUnit result = (CompilationUnit) parser.createAST(null);
	}

	public static ICompilationUnit getCompilationUnit() throws CoreException {

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsRoot = workspace.getRoot();
		IProject project = wsRoot.getProject("MyProject");
		IJavaProject javaProject = JavaCore.create(project);

		IFolder sourceFolder = project.getFolder("src");
		sourceFolder.create(false, true, null);

		IPackageFragmentRoot root = javaProject
				.getPackageFragmentRoot(sourceFolder);
		IPackageFragment pkg = root.getPackageFragment("temp");
		ICompilationUnit unit = pkg.getCompilationUnit("Temp");

		return unit;
	}

}
