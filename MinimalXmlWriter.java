import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.XMLEvent;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class MinimalXmlWriter {

    public static void main(String[] args) {
        try {
            // Create an XMLOutputFactory
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            // Create an XMLEventWriter
            XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream("example.xml"));
            // Create an XMLEventFactory
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            // Create and write the start document event
            XMLEvent startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            // Create and write the start element event
            XMLEvent startElement = eventFactory.createStartElement("", "", "exampleElement");
            eventWriter.add(startElement);
            // Create and write the end element event
            XMLEvent endElement = eventFactory.createEndElement("", "", "exampleElement");
            eventWriter.add(endElement);
            // Create and write the end document event
            XMLEvent endDocument = eventFactory.createEndDocument();
            eventWriter.add(endDocument);
            // Close the writer
            eventWriter.close();
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

