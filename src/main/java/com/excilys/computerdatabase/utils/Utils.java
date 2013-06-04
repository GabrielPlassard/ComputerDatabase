package com.excilys.computerdatabase.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 28/05/13
 * Time: 09:58
 * To change this template use File | Settings | File Templates.
 */
public abstract class Utils {

    private static final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

    static{
        dateFormatter.setLenient(false);
    }

    public static int intParameterOrDefault(String parameter, int defaultValue){
        try{
            return Integer.valueOf(parameter);
        }catch(NumberFormatException e){
            return defaultValue;
        }
    }

    public static String stringParameterOrDefault(String parameter, String defaultValue){
        if (parameter != null) return parameter;
        return defaultValue;
    }

    public static Date dateParameterOrDefault(String date, Date defaultValue) {
        try {
            return dateFormatter.parse(date);
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    public static String format(Date date){
        if (date == null) return null;
        return dateFormatter.format(date);
    }
}
