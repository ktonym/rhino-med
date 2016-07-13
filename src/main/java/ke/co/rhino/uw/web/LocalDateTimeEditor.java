package ke.co.rhino.uw.web;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by akipkoech on 31/10/15.
 */
public class LocalDateTimeEditor extends PropertyEditorSupport {

    private final DateTimeFormatter formatter;
    private final boolean allowEmpty;


    public LocalDateTimeEditor(DateTimeFormatter formatter, boolean allowEmpty) {
        this.formatter = formatter;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public String getAsText() {
        LocalDateTime value = (LocalDateTime) getValue();
        return (value != null ? value.format(this.formatter) : "");
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if ( this.allowEmpty && !StringUtils.hasText(text)){
            setValue(null);
        } else {

            setValue(LocalDateTime.parse(text, formatter));
        }

    }

}