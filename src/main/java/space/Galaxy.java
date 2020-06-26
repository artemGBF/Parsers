package space;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Galaxy {
    private String name;
    private ArrayList<Planet> planets = new ArrayList<>();

    public Galaxy() {
    }

    public Galaxy(String name, ArrayList<Planet> list){
        this.name=name;
        planets=list;
    }

    public Galaxy(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(ArrayList<Planet> planets) {
        this.planets = planets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Galaxy galaxy = (Galaxy) o;
        return Objects.equals(name, galaxy.name) &&
                Objects.equals(planets, galaxy.planets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, planets);
    }

    @Override
    public String toString() {
        return "Galaxy{" +
                "name='" + name + '\'' + "\n " +
                "planets=" + planets +
                '}';
    }

    public void add(Planet a) {
        if (search(a) == -1)
            this.planets.add(a);
    }

    public int search(String planetName) {
        for (int i = 0; i < planets.size(); i++) {
            if (planetName.equals(planets.get(i).getName()))
                return i;
        }
        return -1;
    }

    public int search(Planet a) {
        for (int i = 0; i < planets.size(); i++) {
            if (a.equals(planets.get(i)))
                return i;
        }
        return -1;
    }

    public boolean remove(String planetName) {
        for (Planet planet : this.planets)
            if (planet.getName().equals(planetName)) {
                planets.remove(planet);
                return true;
            }
        return false;
    }

    public boolean remove(Planet a) {
        for (Planet planet : this.planets)
            if (planet.equals(a)) {
                planets.remove(planet);
                return true;
            }
        return false;
    }

    public Planet getPlanet(int index){
        if (index<0||index>this.planets.size())
            return null;
        return this.planets.get(index);
    }

    public String toXML(){
        String s ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<galaxy>" + "<name>" + this.name
                + "</name>" + "<planets>";
        for (Planet planet : this.planets) {
            s += planet.toXML().replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", " ");

        }
        s+="</planets>"+"</galaxy>";
        return s;
    }

    public void toXML(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(toXML());
        bw.close();
    }

    public static Galaxy fromXML(String fileName) throws IOException {
        ArrayList<Planet> list = new ArrayList<>();
        Galaxy gl = new Galaxy();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String stroc="";
        for (int i = 0; i < 3; i++) {
            if(br.ready())
                 stroc= br.readLine();
            if(i==1&&!stroc.equals("<galaxy>"))
                return null;
        }
        stroc = stroc.replace("\t", "");
        String nameLine = stroc.substring(6,stroc.length()-7);
        gl.setName(nameLine);
        while (br.ready()){
            String line = br.readLine();
            line = line.replace("\t", "");
            if (!line.equals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")&&!line.equals("<galaxy>")&&
                    !line.equals("<planets>")&&!line.equals("</planets>")&&!line.equals("</galaxy>")&&
                    !line.equals("<planet>")&&!line.equals("</planet>")){
                String line1 = br.readLine();
                line1 = line1.replace("\t", "");
                String line2 = br.readLine();
                line2 = line2.replace("\t", "");
                String subLine = line.substring(6,line.length()-7);
                String subLine1 = line1.substring(8,line1.length()-9);
                String subLine2 = line2.substring(6,line2.length()-7);
                gl.planets.add(new Planet(subLine, Integer.parseInt(subLine1), Double.parseDouble(subLine2)));
            }
        }
        return gl;
    }

    public static Galaxy fromXMLParser(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(fileName);
        ArrayList<Planet> list = new ArrayList<>();
            String nameG = document.getElementsByTagName("name").item(0).getTextContent();
            NodeList planet = document.getElementsByTagName("planet");
            for (int i = 0; i < planet.getLength(); i++) {
                NodeList childNodesPlant = planet.item(i).getChildNodes();
                Planet planetRes = new Planet();
                for (int k = 0; k < childNodesPlant.getLength(); k++) {
                    if (childNodesPlant.item(k).getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) childNodesPlant.item(k);
                        if (element.getNodeName().equals("name"))
                            planetRes.setName(element.getTextContent());
                        else if (element.getNodeName().equals("radius")) {
                            double radius = Double.parseDouble(element.getTextContent());
                            planetRes.setRadius(radius);
                        } else if (element.getNodeName().equals("mass"))
                            planetRes.setMass(Double.parseDouble(element.getTextContent()));
                    }

                }list.add(planetRes);
            }
            return new Galaxy(nameG, list);
    }
}
