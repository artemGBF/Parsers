package model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.text.SimpleDateFormat;
import java.util.Date;

@XStreamAlias("ROOT")
public class Root {
    private int Stock;
    private String Id;
    private String Name;
    private String Producer;
    private String ProducerCountry;
    private String Pack;
    private String Box;
    private String Metric;
    private Double Price;
    private String Stored;
    private Date Shelflife;
    private String BarCode;
    private Long TNVedCode;

    public Root() {
    }

    @Override
    public String toString() {
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        return "Root{" +
                "Stock='" + Stock + '\'' +
                ", Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Producer='" + Producer + '\'' +
                ", ProducerCountry='" + ProducerCountry + '\'' +
                ", Pack='" + Pack + '\'' +
                ", Box='" + Box + '\'' +
                ", Metric='" + Metric + '\'' +
                ", price='" + Price + '\'' +
                ", Stored='" + Stored + '\'' +
                ", Shelflife='" + f.format(Shelflife) + '\'' +
                ", BarCode='" + BarCode + '\'' +
                ", TNVedCode='" + TNVedCode + '\'' +
                '}';
    }
}
