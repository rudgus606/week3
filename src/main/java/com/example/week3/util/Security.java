package com.example.week3.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Security {
    public static String encryptSHA256(String realPassword){
        String password="";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(password.getBytes());
            byte byteData[]= sh.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i]&0xff)+ 0x100,16).substring(1));
            }
            password= sb.toString();
        } catch (NoSuchAlgorithmException e) {
            password = null;
        }
        return password;
    }
}