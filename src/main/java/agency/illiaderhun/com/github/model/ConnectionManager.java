package agency.illiaderhun.com.github.model;

import org.osjava.sj.loader.SJDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    private static Connection connection = null;
    private static DataSource dataSource = null;
//    private static SJDataSource dataSource = null;

    public static Connection getConnection(){
        try {
            dataSource = (SJDataSource) (new InitialContext().lookup("db"));
            connection = dataSource.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public static DataSource modelConnection(){
        try {

//            dataSource = (SJDataSource) (new InitialContext().lookup("db"));
            Context context = (Context) new InitialContext().lookup("java:comp/env");
            dataSource = (DataSource) context.lookup("jdbc/dbpool");


        } catch (NamingException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    public static DataSource testConnection(){
        try {

            dataSource = (SJDataSource) (new InitialContext().lookup("db"));

        } catch (NamingException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    public static Connection servletConnection(){
        try {
            if (connection != null) {
                connection = null;
            }
            Context context = (Context) new InitialContext().lookup("java:comp/env");
            DataSource dataSource = (DataSource) context.lookup("jdbc/dbpool");

            connection = dataSource.getConnection();

        } catch (NamingException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
