package agency.illiaderhun.com.github.filters;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(RegistrationFilter.class.getSimpleName());

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String NAME_PATTERN ="^[а-яА-ЯёЁa-zA-Z0-9 ]+$";
    private static final String PHONE_PATTERN = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String theCommand = request.getParameter("command");
            LOGGER.info("RegistrationFilter doFilter, the commend: " + theCommand);
            if (theCommand == null){
                theCommand = "INDEX";
            }

            // route to the appropriate method
            switch (theCommand){
                case "REGISTRATION":{
                    if(checkFields(request, response)){
                        chain.doFilter(request, response);
                    } else {
                        request.setAttribute("err", "logPass");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
                        dispatcher.forward(request, response);
                    }
                };break;
                default:{
                    chain.doFilter(request, response);
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Exception " + e);
            throw new ServletException(e);
        }
    }

    private boolean checkFields(ServletRequest request, ServletResponse response) {
        LOGGER.info("checkFields start work");
        boolean result = false;

        String name = request.getParameter("name").trim();
        String email = request.getParameter("email").trim();
        String surname = request.getParameter("surname").trim();
        String password = request.getParameter("password").trim();
        String confirm = request.getParameter("confirm").trim();
        String phone = request.getParameter("phone").trim();

        if (checkName(name) && checkName(surname) && checkEmail(email) && checkPassword(password, confirm) && checkPhone(phone)){
            LOGGER.info("checkFields checked");
            result = true;
        }

        LOGGER.info("checkedFields return: " + result);
        return result;
    }

    private boolean checkPhone(String phone) {
        LOGGER.info("checkPhone start with phone: " + phone);
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phone);

        return matcher.matches();
    }

    private boolean checkPassword(String password, String confirm) {
        LOGGER.info("checkedPassword start pass: " + password + " confirm: " + confirm);
        if (password.length() <= 9 && password.length() > 0){
            LOGGER.info("pass less then 9 symbols");
            if (confirm.length() <= 9 && confirm.length() > 0){
                LOGGER.info("confirm less then 9 symbols");
                LOGGER.info("checkPassword return " + password.equals(confirm));
                return password.equals(confirm);
            }
        }

        LOGGER.info("checkPassword return false");
        return false;
    }

    private boolean checkEmail(String email) {
        LOGGER.info("checkEmail start with email: " + email);
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean checkName(String name) {
        LOGGER.info("checkName start with name: " + name);
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);

        return matcher.matches();
    }

    @Override
    public void destroy() {

    }
}
