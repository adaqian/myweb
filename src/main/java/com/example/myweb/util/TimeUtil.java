package com.example.myweb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String formatDate(String formate, Date time)
    {
        SimpleDateFormat sf = new SimpleDateFormat(formate);
        return sf.format(time).toString();
    }
	
	public static String getSysDate()
    {
        return formatDate("yyyy-MM-dd",new Date());
    }
	
	public static String getSysTime()
    {
        return formatDate("yyyy-MM-dd HH:mm:ss",new Date());
    }

}
