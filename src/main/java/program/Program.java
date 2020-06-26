package program;

import com.thoughtworks.xstream.XStream;
import model.Root;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Date;

public class Program {
    public static void main(String[] args) {
        XStream stream = new XStream();
        //stream.ignoreUnknownElements();
        //stream.alias("n",Root[].class);
        //stream.autodetectAnnotations(true);
        stream.alias("ROOT",Root.class);
        Root mass = (Root) stream.fromXML("neo.xml");
        System.out.println(mass);

      /* Root[] mass = new Root[]{
                new Root(1000,"000000000001000014","Маска 3-х слойн на резин","Hubei Kangning Prot.Products C",
                        "Китай",0,2000,"шт",8.50,3000,new Date(),"487020560942","6307909800")
        };
        stream.autodetectAnnotations(true);
        try {
            FileOutputStream f = new FileOutputStream("neo.xml");
            stream.toXML(mass,f);
        } catch (FileNotFoundException e) {

        }*/


    }
}
