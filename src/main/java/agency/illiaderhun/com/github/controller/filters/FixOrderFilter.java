package agency.illiaderhun.com.github.controller.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixOrderFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(FixOrderFilter.class.getSimpleName());

    private Pattern reportPattern;
    private Matcher matcher;

    private static final String REPORT_PATTERN ="^[а-яА-ЯёЁa-zA-Z0-9.\\- ]+$";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("init param start");

        reportPattern = Pattern.compile(REPORT_PATTERN);

        LOGGER.info("init param end");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("doFilter start");
        try {
            String theCommand = request.getParameter("command");
            LOGGER.info("FixOrderFilter doFilter, the commend: " + theCommand);
            if (theCommand == null){
                theCommand = "INDEX";
            }

            // route to the appropriate method
            switch (theCommand){
                case "FIX_ORDER":{
                    if(checkFields(request, response)){
                        chain.doFilter(request, response);
                    } else {
                        request.setAttribute("err", "regExp");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/userPage.jsp");
                        dispatcher.forward(request, response);
                    }
                };break;
                default:{
                    chain.doFilter(request, response);
                }
            }
        } catch (Exception e) {
            LOGGER.error("caught Exception");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/accessDenied.jsp");
            dispatcher.forward(request, response);
            throw new ServletException(e);
        }
    }

    private boolean checkFields(ServletRequest request, ServletResponse response) {
        LOGGER.info("checkFields start");
        boolean result = false;
        String report = request.getParameter("report");
        if (checkReport(report)){
            result = true;
        }
        return result;
    }

    private boolean checkReport(String report) {
        matcher = reportPattern.matcher(report);

        return matcher.matches();
    }

    @Override
    public void destroy() {

    }
}
