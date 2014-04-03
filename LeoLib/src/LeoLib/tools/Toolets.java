/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.tools;

import LeoLib.utils.HM;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author leo merge @ 2012/3/5
 */
public class Toolets {
    protected static String TAG = Toolets.class.getName();

    /* Data Print */
    public static String println(Object input) {
        System.out.println(input.toString());
        return input.toString();
    }

    public static void printALHM(ArrayList<HashMap> inputALHM) {
        // Check Value
        Iterator<HashMap> ita = inputALHM.iterator();
        while (ita.hasNext()) {
            HashMap itemHM = (HashMap) ita.next();
            printHM(itemHM);
        }
    }

    public static void printHM(HashMap inputHM) {
        /*
         Iterator<String> KeyIt = inputHM.keySet().iterator();
         while (KeyIt.hasNext()) {
         String key = KeyIt.next().toString();
         System.out.println(key + "=" + inputHM.get(key));
         }
         */

        Iterator<String> keyIt = inputHM.keySet().iterator();
        while (keyIt.hasNext()) {
            String key = keyIt.next();
            //System.out.println(obj.getClass().getName());
            if ("java.util.ArrayList".equals(inputHM.get(key).getClass().getName())) {
                println(key + " ::: ");
                printALHM((ArrayList) inputHM.get(key));
            } else {
                println(key + " : " + inputHM.get(key));
            }
        }
    }

    /* Data Check */
    public static boolean notEmpList(List input) {
        return (input != null && input.size() > 0) ? true : false;
    }

    public static boolean isNumber(String input) {
        boolean isNumber = false;

        try {
            Integer check = Integer.parseInt(input);
            isNumber = true;
        } catch (NumberFormatException nfe) {
            isNumber = false;
        }

        return isNumber;
    }

    public static boolean isEmpStr(String input) {
        return (checkTwoStr("", input)) ? true : false;
    }

    public static boolean isNull(Object input) {
        return (input == null) ? true : false;
    }

    public static boolean checkTwoStr(String a, String b) {
        return (a.equals(b)) ? true : false;
    }

    /* String Tool */
    // allow : date , Long(time million second)
    public static String getDateTime(String pattern, Object... dates) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if (dates.length == 1) {
            //System.out.println( dates[0].getClass().getName() );
            if ("java.util.Date".equals(dates[0].getClass().getName())) {
                return sdf.format((Date) dates[0]);
            } else if ("java.lang.Long".equals(dates[0].getClass().getName())) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis((Long) dates[0]);
                //System.out.println("getTimeInMillis : "+cal.getTimeInMillis());
                Date date = cal.getTime();
                return sdf.format(date);
            } else {/* nothing */

            }
        }
        return sdf.format(new Date());
        //( dates.length == 1 ) ? return sdf.format(dates[0]) : return sdf.format(new Date());
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
        for (int i = 0; i < in.length; i++) {
            String hex = Integer.toHexString(0xFF & in[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String getRandomString(int Len) {

        String[] baseString = {"0", "1", "2", "3",
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
            "T", "U", "V", "W", "X", "Y", "Z"};
        Random random = new Random();
        int length = baseString.length;
        String randomString = "";
        for (int i = 0; i < length; i++) {
            randomString += baseString[random.nextInt(length)];
        }
        random = new Random(System.currentTimeMillis());
        String resultStr = "";
        for (int i = 0; i < Len; i++) {
            resultStr += randomString.charAt(random.nextInt(randomString.length() - 1));
        }
        return resultStr;
    }

    public static String urlEncode(String input, String encode) {
        try {
            return URLEncoder.encode(input, encode);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "encoding failed";
        }

    }

    public static String urlDecode(String input, String decode) {
        try {
            return URLDecoder.decode(input, decode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "encoding failed";
        }

    }

    /* Data Sort */
    public static Integer[] sortInt(String... inputs) {
        Integer[] intArr = null;
        if (inputs.length > 0) {
            intArr = new Integer[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
                intArr[i] = Integer.parseInt(inputs[i]);
            }

            Arrays.sort(intArr);
        } else {
            intArr = new Integer[0];
        }

        return intArr;
    }

    // number string with spliter
    public static String sortedString(String spliter, String... inputs) {
        String result = "";

        if (isNumber(spliter)) {
            spliter = ",";
        }// if user forgot to set the spliter

        Integer[] intArr = sortInt(inputs);
        for (int i = 0; i < intArr.length; i++) {
            //System.out.println("intArr : "+intArr[i]);
            result += (i != intArr.length - 1) ? intArr[i] + spliter : intArr[i];
        }

        return result;
    }

    /* File Tool */
    public static String readFile2String(String filePath) {
        try {
            StringBuffer fileData = new StringBuffer(1000);
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
            return fileData.toString();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    
    public static boolean downloadFile(String fileUrl, String fileLocal) {
        try {
            println("Connecting to site...\n");
            URL url = new URL(fileUrl);
            url.openConnection();
            InputStream reader = url.openStream();

            
            LeoLib.tools.Toolets.createParentFolder(fileLocal);
            FileOutputStream writer = new FileOutputStream(fileLocal);
            byte[] buffer = new byte[153600];
            int totalBytesRead = 0;
            int bytesRead = 0;

            println("Reading file 150KB blocks at a time.\n");

            long startTime = System.currentTimeMillis();

            while ((bytesRead = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, bytesRead);
                buffer = new byte[153600];
                totalBytesRead += bytesRead;
            }

            long endTime = System.currentTimeMillis();

            println("Done. " + (new Integer(totalBytesRead).toString()) + " bytes read (" + (new Long(endTime - startTime).toString()) + " millseconds).\n");
            writer.close();
            reader.close();
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println(TAG);
            System.err.println(e);
        }
        return false;
    }
    
    
    public static boolean downloadFile2(String inFileURL, String outFilePath) {
		try {

			//boolean eof = false;

			HttpURLConnection connect = (HttpURLConnection) (new URL(inFileURL))
					.openConnection();
			connect.setRequestMethod("GET");
			connect.setDoOutput(true);
			connect.connect();

			// String PATH_op = Environment.getExternalStorageDirectory() +
			// "/download/" + targetFileName;
			
			LeoLib.tools.Toolets.createParentFolder(outFilePath);
			FileOutputStream file = new FileOutputStream(new File(outFilePath));

			InputStream is = connect.getInputStream();
			byte[] buffer = new byte[1024];
			int len1 = 0;
			while ((len1 = is.read(buffer)) > 0) {
				file.write(buffer, 0, len1);
			}

			file.close();
			return true;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			//e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			//e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			//e.printStackTrace();
		}
		return false;
	}

    public static String parmGetter(HttpServletRequest request, String target) {
        String re;
        re = request.getParameter(target) != null ? request.getParameter(target) : "";
        return re;
    }

    public static void showParm(HttpServletRequest request) {
        Enumeration enu = request.getParameterNames();

        while (enu.hasMoreElements()) {
            String parmKey = enu.nextElement().toString();
            System.out.println("parm key " + parmKey);
            System.out.println("parm value " + request.getParameter(parmKey));
        }

    }

    public static boolean createParentFolder(String filePath) {

        File parent = new File(filePath).getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        //System.out.println("Create Folder." + parent.getAbsolutePath());

        return false;
    }

}
