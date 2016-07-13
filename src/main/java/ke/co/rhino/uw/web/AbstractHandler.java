package ke.co.rhino.uw.web;

import ke.co.rhino.uw.entity.JsonItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.json.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 3/6/15.
 */
public abstract class AbstractHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    static final DateTimeFormatter DATE_FORMAT_yyyyMMdd =  DateTimeFormatter.ofPattern("yyyyMMdd");
    static final DateTimeFormatter DATE_FORMAT_yyyyMMddHHmm = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm");

    @InitBinder
    public void initBinder(WebDataBinder binder){

        binder.registerCustomEditor(LocalDate.class,new LocalDateEditor(DATE_FORMAT_yyyyMMdd,true));
        binder.registerCustomEditor(LocalDateTime.class, new LocalDateTimeEditor(DATE_FORMAT_yyyyMMddHHmm,true));

    }

    public static String getJsonSuccessData(Page<? extends JsonItem> results){
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("success", true);
        builder.add("results", results.getTotalElements());
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        results.forEach( (ji) -> {
                    arrayBuilder.add(ji.toJson());
                }
        );

        builder.add("data", arrayBuilder);
        return toJsonString(builder.build());
    }

    public static String getJsonSuccessData(Stream<? extends JsonItem> results){
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("success", true);
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        results.forEach( (ji) -> {
                    arrayBuilder.add(ji.toJson());
                }
        );

        builder.add("data", arrayBuilder);
        return toJsonString(builder.build());
    }

    public static String getJsonSuccessData(List<? extends JsonItem> results){

        final JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("success", true);
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for(JsonItem ji: results){
            arrayBuilder.add(ji.toJson());
        }

        builder.add("data", arrayBuilder);
        return toJsonString(builder.build());
    }

    public static String getJsonSuccessData(JsonItem jsonItem){

        final JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("success",true)
                .add("data", jsonItem.toJson());

        return toJsonString(builder.build());
    }

    public static String getJsonSuccessData(JsonItem jsonItem, int totalCount){

        final JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("success",true)
                .add("total", totalCount)
                .add("data", jsonItem.toJson());

        return toJsonString(builder.build());

    }

    public static String getJsonErrorMsg(String theErrorMessage){
        return getJsonMsg(theErrorMessage, false);
    }

    public static String getJsonSuccessMsg(String msg){
        return getJsonMsg(msg,true);
    }

    private static String getJsonMsg(String msg, boolean success){

        final JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("success", success)
                .add("msg",msg);

        return toJsonString(builder.build());

    }

    public static String toJsonString(JsonObject model) {

        final StringWriter stWriter = new StringWriter();

        try(JsonWriter jsonWriter = Json.createWriter(stWriter)){
            jsonWriter.writeObject(model);
        }

        return stWriter.toString();

    }

    protected JsonObject parseJsonObject(String jsonString){
        JsonReader reader = Json.createReader(new StringReader(jsonString));
        return reader.readObject();
    }

    protected Integer getIntegerValue(JsonValue jsonValue){

        Integer value = null;

        switch (jsonValue.getValueType()){

            case NUMBER:
                JsonNumber num = (JsonNumber) jsonValue;
                value = num.intValue();
                break;
            case NULL:
                break;
        }

        return value;

    }

    protected JsonArray parseJsonArray(String jsonString){
        JsonReader reader = Json.createReader(new StringReader(jsonString));
        return reader.readArray();
    }

    /**
     * Method to correctly parse Decimal data types
     */
    protected BigDecimal getBigDecimalValue(JsonValue jsonValue){

        BigDecimal value = null;

        switch (jsonValue.getValueType()){

            case NUMBER:
                JsonNumber num = (JsonNumber) jsonValue;
                value = num.bigDecimalValue();
                break;
            case NULL:
                break;
        }

        return value;
    }

}
