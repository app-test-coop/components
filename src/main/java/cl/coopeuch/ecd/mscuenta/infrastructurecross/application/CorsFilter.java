package cl.coopeuch.ecd.mscuenta.infrastructurecross.application;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
    	final HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Methods", "OPTIONS, POST, PUT, GET, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization, channel, track, x-api-key");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        final HttpServletRequest request = (HttpServletRequest) req;
        
        String origin = request.getHeader("origin");
        if (origin == null || origin.trim().length() == 0){
            origin = "*";
        }
        response.setHeader("Access-Control-Allow-Origin", origin);        
        
        
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {    
    }
}
