/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author kjtdi
 */
public class Common {
    
    //get Current Timestamp
    public static Timestamp getTimeStamp() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }
}
