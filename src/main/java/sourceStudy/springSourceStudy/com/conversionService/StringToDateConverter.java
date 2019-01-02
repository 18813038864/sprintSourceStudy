package sourceStudy.springSourceStudy.com.conversionService;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter implements Converter<String,Date> {

    @Override
    public Date convert(String source) {
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.parse(source);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
