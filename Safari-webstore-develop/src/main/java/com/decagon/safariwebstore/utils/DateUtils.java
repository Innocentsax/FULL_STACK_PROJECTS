package com.decagon.safariwebstore.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 
public class DateUtils {
  
    //in hours
    private static Integer resetPasswordExpiryTime = 24;
  
    public static String passwordResetExpiryTimeLimit(){
        Date date = new Date();
      
        //time format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
      
        //calender object to add time to the current time
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, resetPasswordExpiryTime);

        return dateFormat.format(calendar.getTime());
    }
  
    public static String getCurrentTime(){
        Date date = new Date();
      
        //time format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
      
        return dateFormat.format(date);
    }
}
