package org.validator;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import java.io.IOException;

public class XMLValidator {
    public static boolean validateXMLSchema(String xsdPath, String xmlPath) {
        try {
            // Створення фабрики схем і завантаження XSD
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));

            // Створення валідатора з XSD-схеми
            Validator validator = schema.newValidator();

            // Валідація XML-документа
            validator.validate(new StreamSource(new File(xmlPath)));
            return true;
        } catch (IOException | SAXException e) {
            System.out.println("Помилка: " + e.getMessage());
            return false;
        }
    }
}
