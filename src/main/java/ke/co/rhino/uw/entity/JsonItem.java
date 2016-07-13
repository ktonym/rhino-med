package ke.co.rhino.uw.entity;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Created by ktonym on 2/11/15.
 */
public interface JsonItem {

    public JsonObject toJson();
    public void addJson(JsonObjectBuilder builder);

}
