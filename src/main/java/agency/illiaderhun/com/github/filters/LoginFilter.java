package agency.illiaderhun.com.github.filters;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(LoginFilter.class.getSimpleName());

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("LoginFilter start init(filterConfig)");
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String theCommand = request.getParameter("command");
            LOGGER.info("LoginFilter doFilter, the commend: " + theCommand);
            if (theCommand == null){
                theCommand = "INDEX";
            }

            // route to the appropriate method
            switch (theCommand){
                case "LOGIN":{
                    if(checkLoginAndPassword(request, response)){
                        chain.doFilter(request, response);
                    } else {
                        request.setAttribute("err", "logPass");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
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

    private boolean checkLoginAndPassword(ServletRequest request, ServletResponse response) throws ServletException {
        LOGGER.info("checkLoginAndPassword");
        boolean result = false;
        try {
            String email = request.getParameter("email").trim();
            String password = request.getParameter("password").trim();
            if (email != null && validate(email) && checkPassword(password)) {
                result = true;
            }
        } catch (Exception e){
            throw new ServletException();
        }
        LOGGER.info("checkLoginAndPassword return: " + result);
        return result;
    }

    private boolean validate(final String hex) {
        matcher = pattern.matcher(hex);

        return matcher.matches();
    }

    private boolean checkPassword(String password){
        boolean result = false;
        if (password != null && !password.equalsIgnoreCase("")){
            result = true;
        }
        return result;
    }

    @Override
    public void destroy() {

    }
}
