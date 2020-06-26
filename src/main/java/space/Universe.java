package space;



import data.LoadData;
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
import java.util.Timer;
import java.util.TimerTask;

public class Universe {
    public static final double G = 6.67408 * Math.pow(10, -11);
    private ArrayList<Galaxy> galaxies;

    public Universe() {
        this.galaxies = new ArrayList<>();
    }

    public ArrayList<Galaxy> getGalaxies() {
        return galaxies;
    }

    public void setGalaxies(ArrayList<Galaxy> galaxies) {
        this.galaxies = galaxies;
    }

    @Override
    public String toString() {
        return "Universe{" +
                "galaxies=" + galaxies +
                '}';
    }

    public void add(Galaxy a) {
        if (search(a) == -1)
            this.galaxies.add(a);
    }

    public int search(String galacticName) {
        for (int i = 0; i < galaxies.size(); i++) {
            if (galaxies.get(i).getName().equals(galacticName))
                return i;
        }
        return -1;
    }

    public int search(Galaxy a) {
        for (int i = 0; i < galaxies.size(); i++) {
            if (galaxies.get(i).getName().equals(a.getName()))
                return i;
        }
        return -1;
    }

    public void remove(String galacticName) {
        for (Galaxy galaxy : this.galaxies)
            if (galaxy.getName().equals(galacticName))
                galaxies.remove(galaxy);
    }

    public void remove(Galaxy a) {
        for (Galaxy galaxy : this.galaxies)
            if (galaxy.equals(a))
                galaxies.remove(galaxy);
    }

    public static Universe loadData(String filename) throws IOException {
        Universe u = new Universe();
        LoadData ld = new LoadData(filename);
        String[] mass = ld.readData().split("\n");
        for (int i = 0; i < mass.length; i++) {
            Galaxy gl = new Galaxy();
            String[] foo = mass[i].split(" ");
            gl.setName(foo[0]);
            for (int j = 1; j < foo.length - 2; ) {
                Planet pl = new Planet(foo[j], Double.parseDouble(foo[j + 1]), Double.parseDouble(foo[j + 2]));
                j += 3;
                gl.add(pl);
            }
            u.add(gl);
        }
        return u;
    }

    /*public static void behavior() {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            Universe universe = new Universe();

            @Override
            public void run() {
                universe.add(new Rand().randomGl());
                System.out.println(universe);
            }
        }, 0, 2 * 1000);
    }*/

    public String toXML() {
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<universe>" + "<galaxies>";
        for (Galaxy gl : this.galaxies) {
            s += gl.toXML().replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", " ");
        }
        s += "</galaxies>" + "</universe>";
        return s;
    }

    public void toXML(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(toXML());
        bw.close();
    }

    public static Universe fromXML(String fileName) throws IOException {
        ArrayList<Planet> listP = new ArrayList<>();
        ArrayList<Galaxy> listG = new ArrayList<>();
        Universe un = new Universe();
        String NameG = "";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while (br.ready()) {
            String line = br.readLine();
            line = line.replace("\t", "");
            String lineG = "";
            if (line.equals("<galaxy>")) {
                lineG = br.readLine();
                lineG = lineG.replace("\t", "");
                NameG = lineG.substring(6, lineG.length() - 7);
                line = br.readLine();
                line = line.replace("\t", "");
            }
            if (line.equals("<planet>")) {
                String str = "";
                while (!str.equals("</planets>")) {
                    str = br.readLine();
                    str = str.replace("\t", "");
                    if (!str.equals("<planet>") && !str.equals("</planet>") && !str.equals("</planets>")) {
                        str = str.replace("\t", "");
                        String line1 = br.readLine();
                        line1 = line1.replace("\t", "");
                        String line2 = br.readLine();
                        line2 = line2.replace("\t", "");
                        String subLine = str.substring(6, str.length() - 7);
                        String subLine1 = line1.substring(8, line1.length() - 9);
                        String subLine2 = "";
                        if (line2.substring(6, 8).equals("ed")) {
                            //String system = line2.substring(9,11);
                            subLine2 = line2.substring(15, line2.length() - 7);
                        } else
                            subLine2 = line2.substring(6, line2.length() - 7);

                        listP.add(new Planet(subLine, Integer.parseInt(subLine1), Double.parseDouble(subLine2)));
                    }
                }
                Galaxy neo = new Galaxy();
                neo.setName(NameG);
                neo.setPlanets(listP);
                listG.add(neo);
            }
            un.setGalaxies(listG);
        }
        return un;
    }

    public static Universe fromXMLParser(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(fileName);
        ArrayList<Galaxy> galaxies = new ArrayList<Galaxy>();
        Universe a = new Universe();
        NodeList galaxy = document.getElementsByTagName("galaxy");
        for (int j = 0; j < galaxy.getLength(); j++) {
            ArrayList<Planet> planets = new ArrayList<>();
            String nameG = ((Element)galaxy.item(j)).getElementsByTagName("name").item(0).getTextContent();
            NodeList planet = ((Element)galaxy.item(j)).getElementsByTagName("planet");
            for (int i = 0; i < planet.getLength(); i++) {
                NodeList childNodesPlant = planet.item(i).getChildNodes();
                Planet planetRes = new Planet();
                for (int k = 0; k < childNodesPlant.getLength(); k++) {
                    if(childNodesPlant.item(k).getNodeType() == Node.ELEMENT_NODE){
                        Element element = (Element) childNodesPlant.item(k);
                        if(element.getNodeName().equals("name"))
                            planetRes.setName(element.getTextContent());
                        else if(element.getNodeName().equals("radius")) {
                            double radius = Double.parseDouble(element.getTextContent());
                            planetRes.setRadius(radius);
                        }
                        else if(element.getNodeName().equals("mass"))
                            planetRes.setMass(Double.parseDouble(element.getTextContent()));
                    }
                }
                planets.add(planetRes);
            }
            galaxies.add(new Galaxy(nameG, planets));
        }
        a.setGalaxies(galaxies);
        return a;
    }
}

