import agency.illiaderhun.com.github.controller.LoginCommand;
import agency.illiaderhun.com.github.controller.UserPageCommand;
import agency.illiaderhun.com.github.model.entities.RepairOrder;
import agency.illiaderhun.com.github.model.entities.User;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/ControllerDispatcherServlet")
public class ControllerDispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ControllerDispatcherServlet.class.getSimpleName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("ControllerDispatcherServlet doGet");
        choseOption(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("ControllerDispatcherServlet doPost");
        choseOption(request, response);
    }

    private void choseOption(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("choseOption start");
        try {
            String theCommand = request.getParameter("command");

            if (theCommand == null){
                theCommand = "INDEX";
            }
            LOGGER.info("command: " + theCommand);
            // route to the appropriate method
            switch (theCommand){
                case "LOGIN":{
                    loginUser(request, response);
                };break;
                case "USER_PAGE":{
                    showUserPage(request, response);
                };break;
                case "ADD_ORDER":{
                    showUserPage(request, response);
                };break;
                default:{
                    LOGGER.info("default case");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void showUserPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("method showUserPage start");

        Integer userId = (Integer) request.getSession(true).getAttribute("userId");
        Integer userRole = (Integer) request.getSession(true).getAttribute("userRole");
        ArrayList<RepairOrder> repairOrders = new ArrayList<>();
        LOGGER.info("userId for search: " + userId);
        if (userRole == 1) {
            repairOrders = new UserPageCommand().getCustomerOrders(userId);
        } else if (userRole == 2){
            repairOrders = new UserPageCommand().getManagerOrders(userId);
        } else if (userRole == 3){
            repairOrders = new UserPageCommand().getMasterOrders(userId);
        }

        if (repairOrders != null) {
            request.setAttribute("repairOrders", repairOrders);
        } else {
            request.setAttribute("repairOrders", "noOrders");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/userPage.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Send to {@link LoginCommand} email and password
     * if {@link User} exist set session for it
     * else send to registration page
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginCommand loginCommand = new LoginCommand();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User theUser = loginCommand.byEmailFindUserInDB(email, password);

        if (theUser == null){
            request.setAttribute("err", "logPass");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        } else {
            setSessionForUser(theUser,  request.getSession(true));
            showUserPage(request, response);
        }
    }

    private void setSessionForUser(User theUser, HttpSession session){
        Integer userId = theUser.getUserId();
        Integer userRole = theUser.getRoleId();
        String firstName = theUser.getFirstName();
        String lastName = theUser.getLastName();
        String phone = theUser.getPhone();
        String email = theUser.geteMail();

        session.setAttribute("userId", userId);
        session.setAttribute("userRole", userRole);
        session.setAttribute("firstName", firstName);
        session.setAttribute("lastName", lastName);
        session.setAttribute("phone", phone);
        session.setAttribute("email", email);
    }

}
