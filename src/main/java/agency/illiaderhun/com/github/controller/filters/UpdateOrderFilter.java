package agency.illiaderhun.com.github.controller.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateOrderFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(UpdateOrderFilter.class.getSimpleName());

    private Pattern namePattern;
    private Pattern descriptionPattern;
    private Matcher matcher;

    private static final String NAME_PATTERN ="^[а-яА-ЯёЁa-zA-Z0-9 ]+$";
    private static final String DESCRIPTION_PATTERN ="^[а-яА-ЯёЁa-zA-Z0-9.\\- ]+$";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("init param start");

        namePattern = Pattern.compile(NAME_PATTERN);
        descriptionPattern = Pattern.compile(DESCRIPTION_PATTERN);

        LOGGER.info("init param end");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String theCommand = request.getParameter("command");
            LOGGER.info("UpdateOrderFilter doFilter, the command: " + theCommand);
            if (theCommand == null){
                theCommand = "INDEX";
            }

            // route to the appropriate method
            switch (theCommand){
                case "UPDATE_ORDER":{
                    if(checkFields(request, response)){
                        chain.doFilter(request, response);
                    } else {
                        request.setAttribute("err", "regExp");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/updateOrder.jsp");
                        dispatcher.forward(request, response);
                    }
                };break;
                default:{
                    chain.doFilter(request, response);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception " + e);
            throw new ServletException(e);
        }
    }

    private boolean checkFields(ServletRequest request, ServletResponse response) throws ServletException {
        LOGGER.info("checkFields start");
        boolean result = false;
        try {

            String deviceName = request.getParameter("deviceName").trim();
            String description = request.getParameter("description").trim();
            Integer bons = Integer.valueOf(request.getParameter("bons").trim());
            Integer coins = Integer.valueOf(request.getParameter("coins").trim());
            BigDecimal price = BigDecimal.valueOf(bons).add((BigDecimal.valueOf(coins).divide(BigDecimal.valueOf(100))));
            LOGGER.info(deviceName + " " + description + " " + bons + " " + coins + " " + price);
            if (checkName(deviceName) && checkDescription(description)){
                LOGGER.info("checkFields checked");
                result = true;
            }

        }catch (Exception e){
            LOGGER.warn("checkFields caught Exception");
            throw new ServletException();
        }

        LOGGER.info("checkFields return result " + result);
        return result;
    }

    private boolean checkDescription(String description) {
        matcher = descriptionPattern.matcher(description);

        return matcher.matches();
    }

    private boolean checkName(final String deviceName) {
        matcher = namePattern.matcher(deviceName);

        return matcher.matches();
    }

    @Override
    public void destroy() {

    }
}
