package com.excilys.computerdatabase.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: gplassard
 * Date: 24/05/13
 * Time: 16:10
 * To change this template use File | Settings | File Templates.
 */
public class JdbcUtils {

    private JdbcUtils(){}

    private static final Logger logger = LoggerFactory.getLogger(JdbcUtils.class);
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.warn(e.getMessage());
        }
    }


    public static Connection getConnection() {
        return connectionThreadLocal.get();
    }

    public static void openConnection(){
        logger.debug("Opening connection on thread : {}", Thread.currentThread());
        try {
            connectionThreadLocal.set(DriverManager.getConnection("jdbc:mysql://localhost/computer_database", "root", "root"));
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }
    }

    public static void closeStatement(PreparedStatement statement) {
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }
    }

    public static void closeConnection() {
        logger.debug("Closing connection on thread : {}",Thread.currentThread());
        if (connectionThreadLocal.get() != null){
            try {
                connectionThreadLocal.get().close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }
        connectionThreadLocal.remove();
    }

    public static java.sql.Date dateUtilToSql(java.util.Date date) {
        if (date == null) return null;
        return new java.sql.Date(date.getTime());
    }
}
