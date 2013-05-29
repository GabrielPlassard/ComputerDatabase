package com.excilys.computerdatabase.dao;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 24/05/13
 * Time: 16:10
 * To change this template use File | Settings | File Templates.
 */
public class JdbcUtils {

    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/computer_database", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public static void closeStatement(PreparedStatement statement) {
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public static java.sql.Date dateUtilToSql(java.util.Date date) {
        if (date == null) return null;
        return new java.sql.Date(date.getTime());
    }
}
