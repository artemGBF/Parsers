package model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import converters.MyDataConverter;

import java.io.File;
import java.util.Arrays;

public class RootArray {
    @XStreamImplicit(itemFieldName = "ROOT")
    private Root[] array;

    public static RootArray load(String fileName){
        XStream stream = new XStream();
        stream.alias("n0:mt_PriceList_resp", RootArray.class);
        stream.ignoreUnknownElements();
        stream.registerConverter(new MyDataConverter());
        stream.autodetectAnnotations(true);
        return (RootArray) stream.fromXML(new File(fileName));
    }

    @Override
    public String toString() {
        return "RootArray{" +
                "array=" + Arrays.toString(array) +
                '}';
    }
}
