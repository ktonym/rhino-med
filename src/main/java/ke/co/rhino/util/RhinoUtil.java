package ke.co.rhino.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 12/04/2017.
 */
public class RhinoUtil {

    /**
     * @param familySize
     * @return
     */
    public static boolean checkFamSizeFormat(String familySize){
        String patternString = "^((M|m)(\\+\\d{1,2})?)|(X|x)$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(familySize);
        return matcher.matches();
    }

}
