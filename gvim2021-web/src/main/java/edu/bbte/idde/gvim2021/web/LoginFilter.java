package edu.bbte.idde.gvim2021.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebFilter(urlPatterns = {"/index", "/logout"})
@Slf4j
public class LoginFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        //check if user is logged in
        HttpServletRequest httpRequest = (HttpServletRequest) req;

        if (httpRequest.getSession(false) == null) {
            log.info("Request to server, user is not logged in.");
            if (res instanceof HttpServletResponse) {
                HttpServletResponse httpRes = (HttpServletResponse) res;
                httpRes.sendRedirect("login.html");
            }
            return;
        }

        log.info("Got request, user is logged in!");
        chain.doFilter(req, res);
    }
}
