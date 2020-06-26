package program;

import model.RootArray;

public class P1 {
    public static void main(String[] args) {
        RootArray load = RootArray.load("PriceListXML.xml");
        System.out.println(load);
    }
}
