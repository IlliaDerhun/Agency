package agency.illiaderhun.com.github.controller.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter before all jsp pages which sets UTF-8
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class EncodingFilter implements Filter {

    private String code;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        code = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeRequest)){
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        code = null;
    }
}
