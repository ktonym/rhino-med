package ke.co.rhino.uw.web;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by akipkoech on 3/23/15.
 */
public class LocalDateEditor extends PropertyEditorSupport {

    private final DateTimeFormatter formatter;
    private final boolean allowEmpty;


    public LocalDateEditor(DateTimeFormatter formatter, boolean allowEmpty) {
        this.formatter = formatter;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public String getAsText() {
        LocalDate value = (LocalDate) getValue();
        return (value != null ? value.format(this.formatter) : "");
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if ( this.allowEmpty && !StringUtils.hasText(text)){
            setValue(null);
        } else {

            setValue(LocalDate.parse(text, formatter));
        }

    }

}
