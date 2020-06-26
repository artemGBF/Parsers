package program;

import org.xml.sax.SAXException;
import space.Galaxy;
import space.Universe;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        try {
            Galaxy gl = Galaxy.fromXMLParser("Galaxy.xml");
            System.out.println(gl);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
