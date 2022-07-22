package com.rongyu.crm.commons.utils;

import java.util.UUID;

public class UUIDUtil {
    /**
     * 获取UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
