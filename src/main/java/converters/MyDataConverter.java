package converters;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyDataConverter implements Converter {
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    public boolean canConvert(Class clazz) {
        return Date.class.isAssignableFrom(clazz);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        Date date = (Date) value;
        writer.setValue(formatter.format(date));
    }

    public Object unmarshal(HierarchicalStreamReader reader,
                            UnmarshallingContext context) {
        Date calendar;
        try {
            calendar = formatter.parse(reader.getValue());
        } catch (ParseException e) {
            throw new ConversionException(e.getMessage(), e);
        }
        return calendar;
    }
}
