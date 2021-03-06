package com.bluesoft.todoapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
class LoggerFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
         if(request instanceof HttpServletRequest){
             var httpRequest = (HttpServletRequest) request;
             logger.info("[doFilter] " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
         }
         chain.doFilter(request,response);
         logger.info("doFilter post");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
