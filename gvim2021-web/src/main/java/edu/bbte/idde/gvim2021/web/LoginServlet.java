package edu.bbte.idde.gvim2021.web;

import edu.bbte.idde.gvim2021.utilities.Credentials;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/login")
@Slf4j
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        log.info("POST {}", req.getRequestURI());

        if (Credentials.getUsername().equals(username) && Credentials.getPassword().equals(password)) {
            req.getSession().setAttribute("username", username);
            log.info("User logged in successfully.");
            resp.sendRedirect("index");
            return;
        }

        log.info("User had bad credentials.");
        req.getRequestDispatcher("login.html").forward(req, resp);
    }
}
