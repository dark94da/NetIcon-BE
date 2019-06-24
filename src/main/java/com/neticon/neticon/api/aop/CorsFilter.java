package com.neticon.neticon.api.aop;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String origin = httpRequest.getHeader("Origin");
        String headers = "Origin, X-Requested-With, Content-Type, Accept, X-HTTP-Method-Override, "
                + "Cookie, AccessToken";
        httpResponse.addHeader("Access-Control-Allow-Origin", origin);
        httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.addHeader("Access-Control-Allow-Headers", headers);
        httpResponse.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
