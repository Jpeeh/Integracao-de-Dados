/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ficha7a;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderSchemaFactory;
import org.jdom2.input.sax.XMLReaders;
import org.xml.sax.SAXException;

/**
 *
 * @author abs
 */
public class JDOMFunctions_Validar {
    public static Document validarXSD(String xml, String xsd) throws IOException, SAXException {
  
        try {
            SchemaFactory schemafac
                    = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemafac.newSchema(new File(xsd));
            XMLReaderJDOMFactory factory = new XMLReaderSchemaFactory(schema);
            SAXBuilder sb = new SAXBuilder(factory);
            Document doc = sb.build(new File(xml));
            return doc;
        } catch (JDOMException ex) {
            System.out.println("Ficheiro invalido por XSD");
           // Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
           return null;
        }
       
    }

    public static Document validarDTD(String xml) throws IOException {
        try {
            SAXBuilder sb = new SAXBuilder(XMLReaders.DTDVALIDATING);
            Document doc = sb.build(new File(xml));
            return doc;
        } catch (JDOMException ex) {
            //Logger.getLogger(JDOMFunctions_Validar.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ficheiro invalido por DTD");
            return null;
        }

    }

}
