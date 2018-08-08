import agency.illiaderhun.com.github.model.ConnectionManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        Statement myStmt;
        ResultSet myRs;

        try {
            Connection cn = ConnectionManager.getConnection("servlet");
            String sql = "SELECT * FROM agency_test.user;";
            myStmt = cn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()){
                String email = myRs.getString("e_mail");
                out.println(email);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            ConnectionManager.closeConnection();
        }

        out.println("Hello servlet");
    }
}
