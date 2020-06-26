package space;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Planet implements Comparable<Planet>{
    private String name;
    private double radius;
    private double mass;

    public Planet(){}

    public Planet(String name, double radius, double mass) {
        this.name = name;
        this.radius = radius;
        this.mass = mass;
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getRadius() {
        return radius;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return radius == planet.radius &&
                Double.compare(planet.mass, mass) == 0 &&
                Objects.equals(name, planet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, radius, mass);
    }

    @Override
    public String toString() {
        return "Planet{" +
                 name + " "+ radius + " "+
                 mass +
                "}" + "\n";
    }

    public double accelerateOfFreeFall(){
        return Universe.G*this.mass/(Math.pow(this.radius, 2));
    }

    public String behavior(){
        return "name:" + this.name+", g = "+ String.valueOf(accelerateOfFreeFall());
    }

    public String toXML(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<planet>" + "<name>" + this.name + "</name>" + "<radius>" +
                this.radius + "</radius>" + "<mass>"+ this.mass + "</mass>"+"</planet>";
    }

    public void toXML(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(toXML());
        bw.close();
    }

    public static ArrayList<Planet> loadData(String fileName) throws IOException {
        ArrayList<Planet> arr = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while (br.ready()){
            String s = br.readLine();
            String[] str = s.split(";");
            arr.add(new Planet(str[0], Double.parseDouble(str[1]), Double.parseDouble(str[2])));
        }
        return arr;
    }

    public static void oops(ArrayList<Planet> p){
        boolean isSorted = false;
        Planet buf;
        while(!isSorted) {
            isSorted = true;
            for (int i = 0; i < p.size()-1; i++) {
                if(p.get(i).getRadius() == p.get(i+1).getRadius()) {
                    if (p.get(i).getMass()>p.get(i+1).getMass()) {
                        isSorted=false;
                        buf = p.get(i);
                        p.set(i, p.get(i + 1));
                        p.set(i + 1, buf);
                    }
                }
                else if (p.get(i).getRadius() > p.get(i+1).getRadius()){
                    isSorted = false;
                    buf = p.get(i);
                    p.set(i, p.get(i+1));
                    p.set(i+1, buf);
                }
            }
        }
    }

    @Override
    public int compareTo(Planet o) {
        if (this.getRadius()==o.getRadius())
            return Double.compare(this.getMass(), o.getMass());
        else
            return Double.compare(this.getRadius(), o.getRadius());
    }
}
