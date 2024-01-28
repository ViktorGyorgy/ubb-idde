package edu.bbte.idde.gvim2021.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/logout")
@Slf4j
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("{} Logged out", req.getSession().getAttribute("username"));
        req.getSession().invalidate();

        resp.getWriter().write("You are logged out. Goodbye!");
    }
}
