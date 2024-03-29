package javalib.tools;

import static javalib.tools.Toolets.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 *
 * @author Leo Chen
 */
public class StringTool {
    protected static String TAG = StringTool.class.getName();
    
    public static boolean isEmpty(String input) {
    	if( isNull(input) ){return true;}
        return (checkTwoStr("", input));
    }

    public static boolean checkTwoStr(String a, String b) {
    	if ( !isNull(a) && !isNull(b) ){return (a.equals(b));}
        else { return isNull(a) && isNull(b);}
    }
    
    public static String stripHTMLTag(String srcStr) {
        String regex = "<(?![!/]?[ABIU][>\\s])[^>]*>|&nbsp;";
        return srcStr.replaceAll(regex, "");
    }

    public static String md5(String input) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            result = toHexString(digest);
            //System.out.println(result);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String toHexString(byte[] in) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : in) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String getRandomString(int stringLength) {
        String[] baseString = {
            "0", "1", "2", "3",
            "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e",
            "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y",
            "z", "A", "B", "C", "D",
            "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", 
            "Y", "Z"
        };
        Random random = new Random();
        int length = baseString.length;
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomString.append(baseString[random.nextInt(length)]);
        }
        random = new Random(System.currentTimeMillis());
        StringBuilder resultStr = new StringBuilder();
        for (int i = 0; i < stringLength; i++) {
            resultStr.append(randomString.charAt(random.nextInt(randomString.length() - 1)));
        }
        return resultStr.toString();
    }

    // number string with spliter
    public static String sortedString(String spliter, String... inputs) {
        StringBuilder result = new StringBuilder();

        if (isNumber(spliter)) {
            spliter = ",";
        }// if user forgot to set the spliter

        Integer[] intArr = sortInt(inputs);
        for (int i = 0; i < intArr.length; i++) {
            //out.println("intArr : "+intArr[i]);
            result.append((i != intArr.length - 1) ? intArr[i] + spliter : intArr[i]);
        }

        return result.toString();
    }
    
}
