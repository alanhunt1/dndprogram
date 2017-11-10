package initcheck.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateUtil is a helper class to make it easer to deal with java dates.
 * it provides several functions to help deal with conversion between
 * string representations and java.util.Date objects.
 *
 * @author Alan Hunt
 * @version %I% %G%
 */

public class DateUtil {



    public static java.sql.Date getSqlDate(java.util.Date d) {
        return new java.sql.Date(d.getTime());
    }

    /**
     * this returns the current date and time in the
     * dd-MMM-yyyy hh:mm:ss format.
     * @return date Today's Date.
     */
    public static String getDate() {

        // create a date formatter that uses the Oracle date format
        SimpleDateFormat formatter
                = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");

        String format = null;
        format = formatter.format(new java.util.Date());


        // return the oracle date string
        return format;
    }

    /**
     * This returns a java.util.Date converted from a string and
     * a format string.
     * @param date The string containing the date.
     * @param format the format of the date contained in the date parameter.
     * @return date the java.util.Date constructed from the inputs.
     */
    public static java.util.Date getDate(String date, String format) {

        // create a date formatter that uses the input
        SimpleDateFormat formatter
                = new SimpleDateFormat(format);
        formatter.setLenient(false);

        java.util.Date newDate = null;

        try {
            newDate = formatter.parse(date);
            //logger.log("Got Date " + date);
        } catch (java.text.ParseException e) {
            
            newDate = null;
        }

        // return the date
        return newDate;
    }

    /**
     * This returns a string representation of a java.util.Date object
     * in the format specified.
     * @param d The java.util.Date to convert
     * @param f The format to convert the date into.
     * @return a string representation of the date.
     */
    public static String getDateString(java.util.Date d, String f) {

        if (d == null) {
            return "";
        }
        // create a date formatter that uses the Oracle date format
        SimpleDateFormat formatter
                = new SimpleDateFormat(f);

        String format = null;
        format = formatter.format(d);
        

        // return the oracle date string
        return format;
    }

    /**
     * This returns the current year a four digit format, e.g. 2003
     * @return a string representation of the current year.
     */
    public static String getYear() {

        // create a date formatter that uses the Oracle date format
        SimpleDateFormat formatter
                = new SimpleDateFormat("yyyy");

        String format = null;
        format = formatter.format(new java.util.Date());
        

        // return the oracle date string
        return format;
    }

    /**
     * This returns the current month in a two digit format, e.g. 01
     * for January, 02 for February, etc.
     * @return a string representation of the current month number.
     */
    public static String getMonthNumber() {

        // create a date formatter that uses the Oracle date format
        SimpleDateFormat formatter
                = new SimpleDateFormat("MM");

        String format = null;
        format = formatter.format(new java.util.Date());
     

        // return the oracle date string
        return format;
    }

    /**
     * This returns the current month in a string, e.g. January, February, etc.
     * @return a string representation of the current month.
     */
    public static String getMonth() {

        // create a date formatter that uses the Oracle date format
        SimpleDateFormat formatter
                = new SimpleDateFormat("MMMMMMMMMM");

        String format = null;
        format = formatter.format(new java.util.Date());
      

        // return the oracle date string
        return format;
    }

    /**
     * This returns the current day of the month in a string.
     * @return a string representation of the current day in the month.
     */
    public static String getDay() {

        SimpleDateFormat formatter
                = new SimpleDateFormat("dd");

        String format = null;
        format = formatter.format(new java.util.Date());
       

        return format;
    }

    /**
     * This returns the current date incremented by the specified number
     * of days.  This is useful for calculating what date it will be
     * 30 days from now, for date ranges.
     * @param increment the number of days ahead you want
     * @return a date that is the specified number of days in the future
     */
    public static java.util.Date incDate(int increment) {
        java.util.Date now = new java.util.Date();
       
        long time = now.getTime();

        long msec = 1000;
        long sec = 60;
        long min = 60;
        long hrs = 24;
        long inc = increment;
        long i = inc * hrs * min * sec * msec;

        time += i;
        now.setTime(time);

        return now;
    }

    /**
     * This returns a specified date incremented by the specified number
     * of days.  This is useful for calculating what date it will be
     * 30 days from the specified, for date ranges.
     * @param d the date you want to start from
     * @param increment the number of days ahead you want
     * @return a date that is the specified number of days in the future
     */
    public static java.util.Date incDate(java.util.Date d, int increment) {
        java.util.Date now = d;
        if (d == null) {
         
        }
       
        long time = now.getTime();

      

        long msec = 1000;
        long sec = 60;
        long min = 60;
        long hrs = 24;
        long inc = increment;
        long i = inc * hrs * min * sec * msec;

        time += i;
        now.setTime(time);

       

        return now;

    }

		/**
     * This returns a specified date incremented by the specified number
     * of days.  This is useful for calculating what date it will be
     * 30 days from the specified, for date ranges.
     * @param d the date you want to start from
     * @param increment the number of days ahead you want
     * @return a date that is the specified number of days in the future
     */
    public static java.util.Date incHours(java.util.Date d, int increment) {
        java.util.Date now = d;
        if (d == null) {
           
        }
      
        long time = now.getTime();

      

        long msec = 1000;
        long sec = 60;
        long min = 60;
        long hrs = increment;
       
        long i = hrs * min * sec * msec;

        time += i;
        now.setTime(time);

      

        return now;

    }

    /**
     * This returns the current date decremented by the specified number
     * of days.  This is useful for calculating what date it was
     * 30 days ago, for date ranges.
     * @param decrement the number of days back you want
     * @return a date that is the specified number of days in the past
     */
    public static java.util.Date decDate(int decrement) {
        java.util.Date now = new java.util.Date();
        long time = now.getTime();

        // calculate the number of milliseconds in the number of days you are
        // decrementing by.  Make sure to cast to longs, or it will get
        // rounded when you hit the MAX_VALUE for integers, which is the assumed
        // type for static numbers.
        long msec = 1000;
        long sec = 60;
        long min = 60;
        long hrs = 24;
        long dec = decrement;
        long i = dec * hrs * min * sec * msec;

        time -= i;
        now.setTime(time);

        return now;
    }

		/**
     * This returns the current date decremented by the specified number
     * of days.  This is useful for calculating what date it was
     * 30 days ago, for date ranges.
     * @param decrement the number of days back you want
     * @return a date that is the specified number of days in the past
     */
    public static java.util.Date decDate(java.util.Date d, int decrement) {
        
        long time = d.getTime();

        // calculate the number of milliseconds in the number of days you are
        // decrementing by.  Make sure to cast to longs, or it will get
        // rounded when you hit the MAX_VALUE for integers, which is the assumed
        // type for static numbers.
        long msec = 1000;
        long sec = 60;
        long min = 60;
        long hrs = 24;
        long dec = decrement;
        long i = dec * hrs * min * sec * msec;

        time -= i;
        d.setTime(time);

        return d;
    }

    /**
     * This converts a string date from one format to another.
     * @param date - the string date that you want to convert
     * @param inputFormat - the format that the date is currently in
     * @param outputFormat - the format that you want to convert into
     * @return a string representation of the converted date.
     */
    public static String convertDate(String date, String inputFormat,
                                     String outputFormat) {


        // create a date formatter that uses the input format
        SimpleDateFormat inFormatter
                = new SimpleDateFormat(inputFormat);

        // create a date formatter that uses the output format
        SimpleDateFormat outFormatter
                = new SimpleDateFormat(outputFormat);

        String returnVal = null;

        // convert the string to a date using the input format, then
        // back to a string using the output format.
        try {
            java.util.Date d = inFormatter.parse(date);
            returnVal = outFormatter.format(d);
        } catch (java.text.ParseException e) {

        }
        return returnVal;
    }

    /**
     * This returns the number of DAYS between A and B
     * @param a - first date
     * @param b - secord date
     * @return The number of days between the 2 dates
     */
    public static int dateDiffInDays(Date a, Date b) throws Exception {
        if (a == null) {
            throw new Exception("Null date (a)");
        }
        if (b == null) {
            throw new Exception("Null date (b)");
        }
//       / h / m  / s  / msec
        return (int) ((a.getTime() - b.getTime()) / 24 / 60 / 60 / 1000);
    }

		 /**
     * This returns the number of seconds between A and B
     * @param a - first date
     * @param b - secord date
     * @return The number of seconds between the 2 dates
     */
    public static int dateDiffInSeconds(Date a, Date b) throws Exception {
        if (a == null) {
            throw new Exception("Null date (a)");
        }
        if (b == null) {
            throw new Exception("Null date (b)");
        }
//       / h / m  / s  / msec
        return (int) ((a.getTime() - b.getTime())  / 1000);
    }


}
