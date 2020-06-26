package model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;


@XStreamAlias("Product")
public class Product {
    private String name;
    private String desc;
    private Currency price;
    private int qtyOnHand;
    private int minOrderQty;

    @XStreamConverter(value= ToAttributedValueConverter.class)
    public static class Currency {
        public Double price;
        @XStreamAsAttribute
        public String currency;

        public Currency(Double price, String currency) {
            this.price = price;
            this.currency = currency;
        }

        @Override
        public String toString() {
            return " Price: " + price +" "+ currency +"\n";
        }
    }

    public Product() {}

    public Product(String name, String desc,  double price,String currency, int qtyOnHand, int minOrderQty) {
        this.name = name;
        this.desc = desc;
        this.price = new Currency(price,currency);
        this.qtyOnHand = qtyOnHand;
        this.minOrderQty = minOrderQty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public int getMinOrderQty() {
        return minOrderQty;
    }

    public void setMinOrderQty(int minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    @Override
    public String toString() {
        return "Name: " + name +"\n"+
                " Description: " + desc + "\n" +
                " Quantity: "+qtyOnHand+"\n"+
                price.toString()+
                " Min Order Quantity: " + minOrderQty +"\n";
    }


}
