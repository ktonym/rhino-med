package ke.co.rhino;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 11/04/2017.
 */
public class MemberSizeTest {

    public static void main(String[] args) {

        String [] strings = {"M","m+1","M+10","x","X","M+990","X+7"};



        String patternString = "^((M|m)(\\+\\d{1,2})?)|(X|x)$";
        Pattern pattern = Pattern.compile(patternString);

        for (String s: strings) {
            Matcher matcher = pattern.matcher(s);
            if(matcher.matches()){
                System.out.println("Match found: ".concat(s));
            } else {
                System.out.println(s.concat(" did not match RE"));
            }
        }


        // assert (s1.matches("^"));
    }

}
