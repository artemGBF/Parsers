package model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import com.thoughtworks.xstream.io.StreamException;


import java.io.*;

@XStreamAlias("ProductList")
public class ProductList {
    private Product[] listOfProducts;
    private int index;


    public ProductList() {
        this.listOfProducts = new Product[5];
    }

    public Product get(int index) {
        return this.listOfProducts[index];
    }

    public Product[] getMass() {
        Product[] m = new Product[this.index];
        for (int i = 0; i < this.index; i++) {
            m[i] = this.listOfProducts[i];
        }
        return m;
    }

    public ProductList getAvaiblePurchase() {
        ProductList a= new ProductList();
        for (int i = 0; i < index; i++) {
            if (this.listOfProducts[i].getMinOrderQty() < this.listOfProducts[i].getQtyOnHand())
                a.add(this.listOfProducts[i]);
        }
        return a;
    }

    public boolean add(Product a) {
        if (this.index == 5)
            return false;
        this.listOfProducts[this.index++] = a;
        return true;
    }

    public void purchase(Product a) {
        for (int i = 0; i < index; i++) {
            if (this.listOfProducts[i].equals(a)) {
                this.listOfProducts[i].setQtyOnHand(this.listOfProducts[i].getQtyOnHand() - this.listOfProducts[i].getMinOrderQty());
                break;
            }
        }
    }

    public void returnFromCart(Product a) {
        for (int i = 0; i < this.listOfProducts.length; i++) {
            if (a.getName().equals(this.listOfProducts[i].getName())) {
                this.listOfProducts[i].setQtyOnHand(this.listOfProducts[i].getQtyOnHand() + a.getMinOrderQty());
                break;
            }
        }
    }

    public void toXMLAll(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
       /* xStream.processAnnotations(Product.class);
        xStream.processAnnotations(ProductList.class);*/

        // the following tell XStream how to craete myData element:
        // the last arg is the field to be used as element value and all other fields are attributes
        xStream.registerConverter(new ToAttributedValueConverter(Product.Currency.class, xStream.getMapper(),
                xStream.getReflectionProvider(), xStream.getConverterLookup(), "price"));

        //xStream.useAttributeFor(Product.class, "desc");
        /*xStream.aliasField("currency", Product.class, "price");
        заменить имя тега в классе*/

        xStream.toXML(this,fileWriter);
    }
    public void toXMLApp(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        XStream xStream = new XStream();
        xStream.processAnnotations(Product.class);
        xStream.processAnnotations(ProductList.class);
        xStream.registerConverter(new ToAttributedValueConverter(Product.Currency.class, xStream.getMapper(),
                xStream.getReflectionProvider(), xStream.getConverterLookup(), "price"));
        xStream.toXML(this.getAvaiblePurchase(),fileWriter);
    }

    public int fromXML(String fileName) throws StreamException {
        XStream stream = new XStream();
        stream.processAnnotations(ProductList.class);
        stream.processAnnotations(Product.class);

        stream.registerConverter(new ToAttributedValueConverter(Product.Currency.class, stream.getMapper(),
                stream.getReflectionProvider(), stream.getConverterLookup(), "price"));

        int count = this.count();
            ProductList o = (ProductList) stream.fromXML(new File(fileName));
            for (int i = 0; i < o.getMass().length; i++) {
                this.add(o.get(i));
            }
        return this.count()-count;
    }

    public int count() {
        return this.index;
    }

    public boolean isFull() {
        return this.index == 5;
    }

    public boolean isEmpty() {
        return this.index == 0;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < index; i++) {
            s += "Product " + Integer.toString(i + 1) + ":\n";
            s += this.listOfProducts[i].toString() + "\n";
        }
        return s;
    }

}

