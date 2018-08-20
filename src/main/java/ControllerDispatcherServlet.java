import agency.illiaderhun.com.github.controller.FeedbackCommand;
import agency.illiaderhun.com.github.controller.RepairOrderCommand;
import agency.illiaderhun.com.github.controller.ReportCommand;
import agency.illiaderhun.com.github.controller.UserCommand;
import agency.illiaderhun.com.github.model.entities.RepairOrder;
import agency.illiaderhun.com.github.model.entities.User;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
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
            switch (theCommand) {
                case "LOGIN":{
                    loginUser(request, response);
                };break;
                case "USER_PAGE": {
                    showUserPage(request, response);
                };break;
                case "ADD_ORDER": {
                    addOrder(request, response);
                };break;
                case "DELETE_ORDER": {
                    deleteOrder(request, response);
                };break;
                case "LOAD_ORDER": {
                    loadOrder(request, response);
                };break;
                case "UPDATE_ORDER": {
                    updateOrder(request, response);
                };break;
                case "FIX_ORDER": {
                    fixOrder(request, response);
                };break;
                case "REGISTRATION": {
                    registerNewUser(request, response);
                };break;
                case "USER_INFO": {
                    showUserInfoPage(request, response);
                };break;
                case "UPDATE_ROLE": {
                    updateUserRole(request, response);
                };break;
                case "ADD_COMMENT": {
                    addFeedback(request, response);
                };break;
                default:{
                    LOGGER.info("default case");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);
                }
            }
        } catch (Exception e) {
            LOGGER.error("caught Exception");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/accessDenied.jsp");
            dispatcher.forward(request, response);
            throw new ServletException(e);
        }
    }

    private void addFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String comment = request.getParameter("comment");
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));

        new FeedbackCommand().createFeedback(comment, orderId);
        showUserPage(request, response);
    }

    private void updateUserRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("updateUserRole start");
        Integer userIdForFix = Integer.valueOf(request.getParameter("fixedUserId"));
        Integer updatedRoleId = Integer.valueOf(request.getParameter("newUserRole"));
        LOGGER.info("user ID for fix: " + userIdForFix + " new role: " + updatedRoleId);

        new UserCommand().updateRoleIdForUser(userIdForFix, updatedRoleId);

        showUserPage(request, response);
    }

    private void showUserInfoPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer customerId = Integer.valueOf(request.getParameter("customerId"));
        ArrayList<RepairOrder> repairOrders = new RepairOrderCommand().getCustomerOrders(customerId);
        if (repairOrders == null){
            repairOrders = new RepairOrderCommand().getMasterOrders(customerId);
        }
        User theUser = new UserCommand().getUserInfoById(customerId);

        request.setAttribute("repairOrders", repairOrders);
        request.setAttribute("tempUser", theUser);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/moderatorPage.jsp");
        dispatcher.forward(request, response);
    }

    private void registerNewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("registerNewUser start");
        String name = request.getParameter("name").trim();
        String email = request.getParameter("email").trim();
        String surname = request.getParameter("surname").trim();
        String password = request.getParameter("password").trim();
        String phone = request.getParameter("phone").trim();

        UserCommand userCmd = new UserCommand();
        User user = null;


        user = userCmd.createNewUser(email, password, name, surname, phone);
        if (user == null) {
            request.setAttribute("err", "alreadyExist");
            request.setAttribute("existEmail", email);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }

        setSessionForUser(user, request.getSession(true));
        showUserPage(request, response);
    }

    private void fixOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("fixOrder start");
        String breakingDescription = request.getParameter("report");
        LOGGER.info("orderId: " + request.getParameter("orderId"));
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        LOGGER.info("report: " + breakingDescription + " orderId" + orderId);

        new ReportCommand().createNewReport(breakingDescription, orderId);

        showUserPage(request, response);
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("updateOrder start");
        String deviceName = request.getParameter("deviceName");
        String description = request.getParameter("description");
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        Integer customerId = Integer.valueOf(request.getParameter("customerId"));
        // create full price
        Integer bons = Integer.valueOf(request.getParameter("bons").trim());
        Integer coins = Integer.valueOf(request.getParameter("coins").trim());
        BigDecimal price = BigDecimal.valueOf(bons).add((BigDecimal.valueOf(coins).divide(BigDecimal.valueOf(100))));

        new RepairOrderCommand().updateOrder(deviceName, description, price, orderId, customerId);
        LOGGER.info("updateOrder send to showUserPage " + deviceName + " " + description + " "  + price + " "  + customerId);
        showUserPage(request, response);
    }

    private void loadOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("loadOrder start");
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));

        try {
            RepairOrder repairOrder = new RepairOrderCommand().readOrderById(orderId);
            if (repairOrder != null){
                LOGGER.info("loadOrder found order");
                BigDecimal db = repairOrder.getPrice();
                int bon = db.intValue();
                int coins = ((db.subtract(BigDecimal.valueOf(bon))).multiply(BigDecimal.valueOf(100))).intValue();
                request.setAttribute("bons", bon);
                request.setAttribute("coins", coins);
                request.setAttribute("tempOrder", repairOrder);
            }
        } catch (IdInvalid idInvalid) {
            LOGGER.error("caught Exception");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/accessDenied.jsp");
            dispatcher.forward(request, response);
            idInvalid.printStackTrace();
        }

        LOGGER.info("loadOrder send to updateOrder.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/updateOrder.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        new RepairOrderCommand().deleteOrder(orderId);
        showUserPage(request, response);
    }

    private void addOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deviceName = request.getParameter("deviceName");
        String description = request.getParameter("description");
        Integer userId = (Integer) request.getSession(true).getAttribute("userId");

        new RepairOrderCommand().createNewOrder(deviceName, description, userId);

        showUserPage(request, response);
    }

    private void showUserPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("method showUserPage start");

        Integer userId = (Integer) request.getSession(true).getAttribute("userId");
        Integer userRole = (Integer) request.getSession(true).getAttribute("userRole");
        ArrayList<RepairOrder> repairOrders = new ArrayList<>();
        LOGGER.info("userId for search: " + userId);
        if (userRole == 1) {
            repairOrders = new RepairOrderCommand().getCustomerOrders(userId);
        } else if (userRole == 2){
            repairOrders = new RepairOrderCommand().getManagerOrders(userId);
        } else if (userRole == 3){
            repairOrders = new RepairOrderCommand().getMasterOrders(userId);
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
     * Send to {@link agency.illiaderhun.com.github.controller.UserCommand} email and password
     * if {@link User} exist set session for it
     * else send to registration page
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserCommand loginCommand = new UserCommand();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User theUser = loginCommand.validateUserByEmailPassword(email, password);

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
