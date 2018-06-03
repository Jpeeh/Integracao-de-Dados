/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhopratico;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderSchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author abs
 */
public class JDOMFunctions_Validar {
    public static Document validarXSD(String xml) throws IOException, SAXException {
        try {
            SAXBuilder builder = new SAXBuilder(); // true ativa a validação
            
            // esta linha ativa a validação com XSD
            builder.setFeature("http://apache.org/xml/features/validation/schema", true);

            Document doc = builder.build(new File(xml));
            System.out.println("Documento XML " + xml + " é válido (XSD)");
            return doc;
        } catch (JDOMException ex) {
            System.out.println("Documento XML " + xml + " apresenta erros e não é válido (XSD)");
            //Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Documento XML " + xml + " nao foi encontrado");
            //Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Document validarDTD(String xml) throws IOException {
        try {
            SAXBuilder builder = new SAXBuilder();  // true ativa a validação
            //Document doc = builder.build(new File(caminhoFicheiro));
            Document doc = builder.build(xml);
            
            System.out.println("Documento XML " + xml + " é válido (DTD)");
            return doc;
        } catch (JDOMException ex) {
            System.out.println("Documento XML " + xml + " apresenta erros e não é válido (DTD)");
            //Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Documento XML " + xml + " nao foi encontrado");
            //Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
