package edu.bbte.idde.gvim2021.web;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import edu.bbte.idde.gvim2021.apartmentad.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.gvim2021.apartmentad.backend.dao.CommentDao;
import edu.bbte.idde.gvim2021.apartmentad.backend.model.Comment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/comments")
@Slf4j
public class CommentServlet extends HttpServlet {
    private static final CommentDao COM_DAO =
            AbstractDaoFactory.getCommentDao();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.info("GET /comments");

        resp.setHeader("Content-type", "application/json");

        //if id != null, return apartment with id
        String id = req.getParameter("id");
        log.debug("THE ID IS {}", id);

        if (id != null) {
            Long idAsLong;
            try {
                idAsLong = Long.parseLong(id);
            } catch (NumberFormatException e) {
                resp.sendError(400, "Type of Id is not long.");
                return;
            }

            Comment comment = COM_DAO.findById(idAsLong);

            if (comment == null) {
                resp.sendError(404, "Comment with id not found.");
                return;
            }

            objectMapper.writeValue(resp.getWriter(), comment);
            return;
        }

        String author = req.getParameter("author");
        if (author != null) {
            objectMapper.writeValue(resp.getWriter(), COM_DAO.findByAuthor(author));
            return;
        }

        objectMapper.writeValue(resp.getWriter(), COM_DAO.findAll());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("POST /comments");
        Comment comment;

        try {
            comment = objectMapper.readValue(req.getInputStream(), Comment.class);
        } catch (UnrecognizedPropertyException | JsonParseException | InvalidFormatException e) {
            log.error("Invalid comment json.");
            resp.sendError(400, "Invalid comment json.");
            return;
        }

        if (isNullFieldInComment(comment)) {
            log.error("Invalid comment has null field.");
            resp.sendError(400, "comment has null field.");
            return;
        }

        log.info("received comment {}", comment);
        COM_DAO.create(comment);

        resp.getWriter().write("Comment added successfully");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("POST /comments");

        Long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Id is not present/not valid.");
            return;
        }

        Comment comment;
        try {
            comment = objectMapper.readValue(req.getInputStream(), Comment.class);
        } catch (UnrecognizedPropertyException | JsonParseException | InvalidFormatException e) {
            log.error("Invalid comment json.");
            resp.sendError(400, "Invalid comment json.");
            return;
        }

        if (isNullFieldInComment(comment)) {
            log.error("Invalid comment has null field.");
            resp.sendError(400, "comment has null field.");
            return;
        }

        if (COM_DAO.findById(id) == null) {
            resp.sendError(404, "Comment with such id is not present.");
            return;
        }

        log.info("Patched comment with id {} to {}", id, comment);
        COM_DAO.update(id, comment);

        resp.getWriter().write("Succesfully changed the comment");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("DELETE /comments");
        Long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Id is not present/not valid.");
            return;
        }

        COM_DAO.delete(id);
        resp.getWriter().write("Comment deleted if it was present");
    }

    private boolean isNullFieldInComment(Comment comment) {
        return comment.getAuthor() == null
                || comment.getText() == null
                || comment.getApartmentAdId() == null;
    }
}
