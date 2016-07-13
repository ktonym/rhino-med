package ke.co.rhino.uw.vo;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 27/01/2016.
 */
public class Result<T> implements Serializable {

    final private boolean success;
    final private T data;
    final private String msg;

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
        this.msg = null;
    }

    public Result(boolean success, String msg) {
        this.success = success;
        this.data = null;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int hash = 7;
        hash = prime * hash + (this.success ? 1 : 0);
        hash = prime * hash + Objects.hashCode(this.data);
        return hash;

    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        final Result<?> other = (Result<?>) obj;
        if (this.success != other.success){
            return false;
        }
        return Objects.deepEquals(this.data, other.data);

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("\"Result{\"");
        sb.append("success=").append(success);
        sb.append(", msg=").append(msg);

        sb.append(", data=");

        if(data == null){

            sb.append("null");

        } else if(data instanceof Stream){

            Stream castStream = (Stream) data;
            if(castStream.count() == 0){
                sb.append("Empty stream");
            } else {
                Object firstItem = castStream.findFirst();
                sb.append("Stream of ").append(firstItem.getClass());
            }

        } else if(data instanceof List){

            List castList = (List) data;
            if(castList.isEmpty()){
                sb.append("empty list");
            } else {
                Object firstItem = castList.get(0);
                sb.append("List of ").append(firstItem.getClass());
            }

        } else if(data instanceof Page){
            Page castPage = (Page) data;
            if (castPage.getTotalPages() == 0){
                sb.append("No pages");
            } else {
                Object firstItem = castPage.getContent().get(0);
                sb.append("List of ").append(firstItem.getClass());
            }
        } else {
            sb.append(data.toString());
        }

        sb.append("}");

        return sb.toString();

    }
}

