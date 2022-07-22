package com.rongyu.UUIDTest;

import java.util.UUID;

public class TestUUID {
    public static void main(String[] args) {
        String str = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println(str);
    }
}
