package agency.illiaderhun.com.github.controller.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter before all pages
 * In case some user wanna go to not valid jsp page
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class PageRedirectSecurityFilter implements Filter {

    private String indexPath;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter("INDEX_PATH");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
