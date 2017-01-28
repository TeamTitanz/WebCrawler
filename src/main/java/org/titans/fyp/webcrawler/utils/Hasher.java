/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.titans.fyp.webcrawler.utils;

import java.security.MessageDigest;

/**
 *
 * @author kjtdi
 */
public class Hasher {
    
    public static String toSha256(String inString) throws Exception {
        //Hashing using SHA-256
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        String text = inString.toLowerCase();
        messageDigest.update(text.getBytes("ASCII"));
        byte[] hash = messageDigest.digest();
            
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hash) {
            stringBuilder.append(String.format("%02x", b));
        }
           
        return stringBuilder.toString().toUpperCase(); 
    } 
}
