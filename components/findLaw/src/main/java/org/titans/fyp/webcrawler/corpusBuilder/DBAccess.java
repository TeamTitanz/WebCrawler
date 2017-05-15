package org.titans.fyp.webcrawler.corpusBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vindula Jayawardana
 */
public class DBAccess {

    public static ResultSet getData(Connection connection, String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    public static boolean setData(Connection connection, String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        int rowCount = statement.executeUpdate();
        return (rowCount > 0) ? true : false;
    }


    public static boolean setData(Connection connection, String query, Object[] ob) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < ob.length; i++) {
            statement.setObject(i + 1, ob[i]);
        }
        int rowCount = statement.executeUpdate();

        return (rowCount > 0) ? true : false;
    }

    public static ResultSet getData(Connection connection, String query, Object[] ob) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < ob.length; i++) {
            statement.setObject(i + 1, ob[i]);
        }
        return statement.executeQuery();
    }

}
