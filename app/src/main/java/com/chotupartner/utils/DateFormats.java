package com.chotupartner.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormats {
    public static String homeDateFormat(long milies) {
        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy hh:mm a", Locale.US);

        return df.format(new Date(milies));
    }


    public static String scheduleDateFormat(long milies) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("IST"));
        return df.format(new Date(milies));
    }

    public static String reverseDate(String date) {

        String data[] = date.split("-");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date inputdate = input.parse(date);                 // parse input
            return output.format(inputdate);    // format output
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "NA";
    }


    public static String formatTime(String time) {

        try {
            String _24HourTime = time;
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            System.out.println(_24HourDt);
           return _12HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }
}

