import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection connection = null;
    public static Connection getNewConnection() {
        try {
            Context context = (Context) (new InitialContext().lookup("java:comp/env"));
            DataSource dataSource = (DataSource) context.lookup("jdbc/dbpool");

            connection = dataSource.getConnection();

            return connection;
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
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
