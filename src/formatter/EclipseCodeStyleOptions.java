package formatter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jdt.core.JavaCore;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Programmatically apply code styles via eclipse pde
 * @author deniz.turan (http://denizstij.blogspot.com/) Oct-2009
 */
public class EclipseCodeStyleOptions {

    private static Map<String,String> getCodeStyleEntries(File xmlFile) 
            throws ParserConfigurationException, SAXException, IOException {
        Map<String,String> entries = new HashMap<String,String>();
               
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
      
        NodeList nList = doc.getElementsByTagName("setting");
             
        for (int temp = 0; temp < nList.getLength(); temp++) {     
            Node nNode = nList.item(temp);          
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {     
                Element eElement = (Element) nNode;
                String id = eElement.getAttribute("id");
                String value = eElement.getAttribute("value");
                entries.put(id,value);
            }
        }
        return entries;
    }

    /** 
     * Return data structure compatible with @see JavaCore.getOptions
     */
    public static Hashtable getCodeStyleSettingOptions(File codeStyleFile) {
        Map<String,String> codeStyleOptions = null;
        try {
            codeStyleOptions = getCodeStyleEntries(codeStyleFile);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Hashtable options = JavaCore.getDefaultOptions();
        options.putAll(codeStyleOptions);
        return options;        
    }

    
}
