import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/ServletName")
public class ServletName extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletName.class.getSimpleName());
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        out.println("hello");
        LOGGER.info("Start servlet");
        Context envCtx = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        try {
            /*envCtx = (Context) (new InitialContext().lookup("java:comp/env"));
            DataSource ds = (DataSource) envCtx.lookup("jdbc/dbpool");

            Connection cn = ds.getConnection();*/
            Connection cn = ConnectionManager.getNewConnection();
            String sql = "SELECT * FROM agency_test.user;";
            myStmt = cn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()){
                String email = myRs.getString("e_mail");
                out.println(email);
            }

        }/* catch (NamingException e) {
            e.printStackTrace();
        }*/ catch (SQLException ex) {
            ex.printStackTrace();
        }

        out.println("Hello servlet");
    }
}
