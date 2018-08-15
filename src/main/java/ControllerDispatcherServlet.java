import agency.illiaderhun.com.github.controller.LoginCommand;
import agency.illiaderhun.com.github.controller.RepairOrderCommand;
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
            switch (theCommand){
                case "LOGIN":{
                    loginUser(request, response);
                };break;
                case "USER_PAGE":{
                    showUserPage(request, response);
                };break;
                case "ADD_ORDER":{
                    addOrder(request, response);
                };break;
                case "DELETE_ORDER":{
                    deleteOrder(request, response);
                };break;
                case "LOAD_ORDER":{
                    loadOrder(request, response);
                };break;
                case "UPDATE_ORDER":{
                    showUserPage(request, response);
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
