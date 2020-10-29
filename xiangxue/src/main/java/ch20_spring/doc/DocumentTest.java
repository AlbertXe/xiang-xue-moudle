package ch20_spring.doc;

import org.junit.Test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DocumentTest {

    @Test
    public void test() throws ParserConfigurationException {
        DocumentBuilderFactory facroty = DocumentBuilderFactory.newInstance();
        facroty.setNamespaceAware(false);
        facroty.setValidating(false);
        DocumentBuilder documentBuilder = facroty.newDocumentBuilder();
//        documentBuilder.setEntityResolver(()->{});


    }
}
