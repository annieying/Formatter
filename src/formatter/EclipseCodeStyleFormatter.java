package formatter;

import java.io.File;
import java.util.Hashtable;

import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

/**
 * Programmatically apply code styles via eclipse pde
 * based on code from @see deniz.turan (http://denizstij.blogspot.com/) Oct-2009
 */
public class EclipseCodeStyleFormatter { 
	
	public static String dir = "./res/eclipse-code-style-files/";
	public static String FILE_VERTICALLY_LONG_STYLE = dir + "vertically-long.xml";
	public static String FILE_ECLIPSE_BUILT_IN_STYLE = dir + "eclipse-built-in.xml";
	public static String FILE_COMPACT_STYLE = dir + "compact.xml";

    public static String format(String code, File codeStyleFile) {
    	
          IDocument document = new Document(code);
          Hashtable codeStyleOptions = EclipseCodeStyleOptions.getCodeStyleSettingOptions(codeStyleFile);
          
          CodeFormatter formatter = ToolFactory.createCodeFormatter(
                  codeStyleOptions);
          
        try {              
              int indentationLevel = 0;
              TextEdit edit = formatter.format(
                      CodeFormatter.K_UNKNOWN,
                      code,0,code.length(),indentationLevel, null);

              if(edit != null)
                edit.apply(document);
        } catch (MalformedTreeException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

          String formattedCode = document.get();
          
          return formattedCode;
    }
     

}
