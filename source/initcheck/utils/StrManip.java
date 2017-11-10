package initcheck.utils;

import initcheck.InitLogger;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.StringTokenizer;

/**
 * StrManip is a set of helper methods for string manipulation, like
 * escaping control characters, and doing substring replaces.
 *
 * @author Alan Hunt
 * @version 1.0
 */

public class StrManip {
		InitLogger logger = new InitLogger(this, "defaultLog4j.cfg");

    /**
     * escape all instances of target in string s with escape sequence
     * escapeChar.  For example, calling (for an oracle escape)
     * escape ("Alan's Lousy Code", "'", "'"), would return the
     * escaped string "Alan''s lousy code".
     * @param s the string to escape
     * @param escapeChar the character or string to use for escaping
     * @param target the string to escape.
     * @return the escaped string
     */
    public static String escape(String s, String escapeChar, String target) {

        if (s == null || s.indexOf(target) < 0) {
           
            return s;
        }


        StringTokenizer split = new StringTokenizer(s, target);
        String escapedString = "";

        while (split.hasMoreTokens()) {
            String token = split.nextToken();
           
            escapedString += token;
            if (split.hasMoreTokens()) {
                escapedString += escapeChar + target;
            }
        }
        if (s.substring(s.length() - target.length()).equals(target)) {
            escapedString += escapeChar + target;
        }
       
        return escapedString;
    }

    /**
     * remove all instances of a target string from the input string.
     * for example, calling remove("Alan! What A Great Guy!", "!") would
     * return the string "Alan What A Great Guy".
     * @param s the input string.
     * @param target the string to remove
     * @return the modified string
     */
    public static String remove(String s, String target) {
        if (s == null || s.indexOf(target) < 0) {
         
            return s;
        }

				
        String escapedString = s;

        while (escapedString.indexOf(target) >= 0) {
            escapedString = escapedString.substring(0, escapedString.indexOf(target)) +
								escapedString.substring(escapedString.indexOf(target) + target.length(), escapedString.length());

        }
        
        return escapedString;
    }

    /**
     * Replace all instances of a specified substring with another string.
     * for example, calling replace("This code is :-(", ":-(", ":-)") will
     * return the string "This code is :-)".
     * @param s the string to do the replace on
     * @param target the substring to replace
     * @param replace the string to replace it with
     * @return the modified string.
     */
    public static String replace(String s, String target, String replace) {
        if (s == null || s.indexOf(target) < 0) {
            
            return s;
        }

        String escapedString = s;

        while (escapedString.indexOf(target) >= 0) {
            escapedString = escapedString.substring(0, escapedString.indexOf(target)) +
                    replace +
                    escapedString.substring(escapedString.indexOf(target) + target.length(), escapedString.length());

        }
       
        return escapedString;
    }

    /**
     * oracle escape any single quotes in the target string.
     * Notes : this takes a string and converts all single quotes to
     *         escaped single quotes.  This avoids problems when inserting
     *         in tables, since the single quote is the field delimiter
     *         character in Oracle.
     * @param s - the raw string that you wish to escape
     *
     * @return String - the string with quotes escaped
     *
     */
    public static String escapeQuotes(String s) {

        if (s == null || s.indexOf("'") < 0) {
            return s;
        }

        StringTokenizer split = new StringTokenizer(s, "'");
        String escapedString = "";

        while (split.hasMoreTokens()) {
            String token = split.nextToken();
						escapedString += token;
            if (split.hasMoreTokens()) {
                escapedString += "''";
            }
        }
        if (s.charAt(s.length() - 1) == '\'') {
            escapedString += "''";
        }

        return escapedString;
    }

    /**
		 * Describe <code>isNullOrEmpty</code> method here.
		 *
		 * @param s a <code>String</code> value
		 * @return a <code>boolean</code> value
		 */
		public static boolean isNullOrEmpty(String s) {
        if (s == null || s.equals("")) {
            return true;
        } else {
            return false;
        }
    }

		 /**
		 * Describe <code>isNullOrEmpty</code> method here.
		 *
		 * @param s a <code>String</code> value
		 * @return a <code>boolean</code> value
		 */
		public static boolean isNullZeroOrEmpty(String s) {
        if (s == null || s.equals("") || s.equals("0")) {
            return true;
        } else {
            return false;
        }
    }

		
		
		 /**
		 * Describe <code>isNullOrEmpty</code> method here.
		 *
		 * @param s a <code>String</code> value
		 * @return a <code>boolean</code> value
		 */
		public static boolean isNullValOrEmpty(String s) {
        if (s == null || s.equals("") || s.equalsIgnoreCase("null")) {
            return true;
        } else {
            return false;
        }
    }

    /**
		 * Describe <code>formatNumber</code> method here.
		 *
		 * @param s a <code>String</code> value
		 * @return a <code>String</code> value
		 */
		public static String formatNumber(String s) {
        if (s == null || s.equals("null") || s.trim().equals("")) {
            return "0";
        }
        return s;
    }

		public static String formatPercent(String s) {

        if (s == null || s.equals("null") || s.trim().equals("")) {
            return "";
        }
        String currStr = "0.00";

        try {

            double myNumber = Double.parseDouble(s) * 100;
            DecimalFormat form = new DecimalFormat("0.00");
            currStr = form.format(myNumber) + "%";

        } catch (Exception e) {
            
        }
        return currStr;
    }


    /**
		 * Describe <code>formatDecimal</code> method here.
		 *
		 * @param s a <code>String</code> value
		 * @return a <code>String</code> value
		 */
		public static String formatDecimal(String s) {

        if (s == null || s.equals("null") || s.trim().equals("")) {
            return "0.00";
        }
        String currStr = "0.00";

        try {

            double myNumber = Double.parseDouble(s);
            DecimalFormat form = new DecimalFormat("0.00");
            currStr = form.format(myNumber);

        } catch (Exception e) {
            //logger.log("error", "Error converting to decimal " + e);
        }
        return currStr;
    }

    /**
		 * Describe <code>formatMoney</code> method here.
		 *
		 * @param s a <code>String</code> value
		 * @return a <code>String</code> value
		 */
		public static String formatMoney(String s) {
        if (s == null || s.equals("null") || s.trim().equals("")) {
            return "$0.00";
        }
        String currStr = "$0.00";

        try {

            double myNumber = Double.parseDouble(s);
            NumberFormat form = NumberFormat.getCurrencyInstance();
            currStr = form.format(myNumber);

        } catch (Exception e) {
            //logger.log("error", "Error converting to currency " + e);
        }
        return currStr;

    }

    /**
		 * format checks a string to see if it is null, or if it equals the 
		 * string "null".  If so, it retuns an empty string.  Otherwise, it
		 * returns the string which was passed in.
		 *
		 * @param s a <code>String</code> value
		 * @return a <code>String</code> value
		 */
		public static String format(String s) {
        if (s == null || s.equals("null")) {
            return "";
        }
        return s;
    }
		
    /**
		 * formatHTML checks a string to see if it is null, or if it equals the 
		 * string "null".  If so, it retuns "&nbsp;".  Otherwise, it
		 * returns the string which was passed in.
		 *
		 * @param s a <code>String</code> value
		 * @return a <code>String</code> value
		 */
		public static String formatHTML(String s) {
        if (s == null || s.equals("null") || s.trim().equals("")) {
            return "&nbsp;";
        }
        return s;
    }

		public static String pad(String s, int l){
			
				if (s == null){
						s = "";
				}

				if (s.length() > l){
						return s.substring(0, l);
				}

				char[] chars = new char[l];
				for (int i = 0; i < s.length(); i++){
						chars[i] = s.charAt(i);
				}
				for (int i = s.length(); i < l; i++){
						chars[i] = ' ';
				}
				return new String(chars);
		}


    public static boolean isNumber(String s){
	 try {
	      //int i = Integer.parseInt(s);
          @SuppressWarnings("unused") double d = Double.parseDouble(s); //support float!
	      return true;
       }catch(Exception e){
		return false;
       }
    }
    
    public static String trimLast(String s, char trim){
    	if (s != null && s.length() > 0){
    		if (s.charAt(s.length() - 1) == trim) {
    			s = s.substring(0, s.length() - 1);
    		}
    	}
    	return s;
    }
}
