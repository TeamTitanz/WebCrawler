/*******************************************************************************
 * Copyright 2016 Titans
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 ******************************************************************************/

package org.titans.fyp.webcrawler.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBHandler {

    //use to write data to db
    public static int setData(String query, Connection connection) throws SQLException{
        java.sql.Statement stm =  connection.createStatement();
        int res = stm.executeUpdate(query , stm.RETURN_GENERATED_KEYS);
        return res;
    }

    //use to read data from db
    public static ResultSet getData(String query, Connection connection) throws SQLException{
        java.sql.Statement stm =  connection.createStatement();
        ResultSet res = stm.executeQuery(query);
        return res;
    }

    
}
