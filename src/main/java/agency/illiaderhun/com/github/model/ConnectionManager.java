package agency.illiaderhun.com.github.model;

import org.apache.log4j.Logger;
import org.osjava.sj.loader.SJDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Create connection dependence you type need
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class ConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getSimpleName());

    private static Connection connection = null;
    private static DataSource dataSource = null;

    /**
     * Create connection for work space and web module
     *
     * @return valid connection
     */
    public static Connection getConnection(){
        LOGGER.info("getConnection start");
        try {
            dataSource = (SJDataSource) (new InitialContext().lookup("db"));
            connection = dataSource.getConnection();
        } catch (NamingException e) {
            LOGGER.error("getConnection caught NamingException");
            LOGGER.trace(e);
            e.printStackTrace();
        } catch (SQLException ex) {
            LOGGER.error("getConnection caught SQLException");
            LOGGER.trace(ex);
            ex.printStackTrace();
        }

        LOGGER.info("getConnection return: " + connection);
        return connection;
    }

    /**
     * Create data source for any model from DAO
     *
     * @return data source for further connection creating
     */
    public static DataSource modelConnection(){
        LOGGER.info("modelConnection start");
        try {

            Context context = (Context) new InitialContext().lookup("java:comp/env");
            dataSource = (DataSource) context.lookup("jdbc/dbpool");

        } catch (NamingException e) {
            LOGGER.error("modelConnection caught NamingException");
            LOGGER.trace(e);
            e.printStackTrace();
        }

        LOGGER.info("modelConnection return: " + dataSource);
        return dataSource;
    }

    /**
     * Create data source for JUnit testing
     *
     * @return data source for further connection creating
     */
    public static DataSource testConnection(){
        LOGGER.info("testConnection start");
        try {

            dataSource = (SJDataSource) (new InitialContext().lookup("db"));

        } catch (NamingException e) {
            LOGGER.error("testConnection caught NamingException");
            LOGGER.trace(e);
            e.printStackTrace();
        }

        LOGGER.info("testConnection return: " + dataSource);
        return dataSource;
    }

    /**
     * Create connection for Servlet
     *
     * @return ready connection for work with DB
     */
    public static Connection servletConnection(){
        LOGGER.info("servletConnection start");
        try {
            if (connection != null) {
                LOGGER.info("create new connection");
                connection = null;
            }
            Context context = (Context) new InitialContext().lookup("java:comp/env");
            DataSource dataSource = (DataSource) context.lookup("jdbc/dbpool");

            connection = dataSource.getConnection();

        } catch (NamingException ex) {
            LOGGER.error("servletConnection caught NamingException");
            LOGGER.trace(ex);
            ex.printStackTrace();
        } catch (SQLException e) {
            LOGGER.error("servletConnection caught SQLException");
            LOGGER.trace(e);
            e.printStackTrace();
        }

        LOGGER.info("servletConnection return: " + connection);
        return connection;
    }

    public static void closeConnection(){
        LOGGER.info("closeConnection start");
        if (connection != null) {
            LOGGER.info("connection not null");
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("closeConnection caught SQLException ");
                LOGGER.trace(e);
                e.printStackTrace();
            }
        }
    }

}
