package ke.co.rhino.uw.repo;

/**
 * Created by user on 25/03/2017.
 */
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
    private Object optVal;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SearchCriteria(String key, String operation, Object value, Object optVal) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.optVal = optVal;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getOptVal() {
        return optVal;
    }

    public void setOptVal(Object optVal) {
        this.optVal = optVal;
    }
}
