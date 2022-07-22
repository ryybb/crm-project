package com.rongyu.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 将日期类型转换为字符串
     * @param date
     * @return
     */

    public static String formatDateStr(Date date){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sf.format(date);
        return dateStr;
    }
}
