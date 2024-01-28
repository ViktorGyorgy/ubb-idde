package edu.bbte.idde.gvim2021.spring.dao.database;

import edu.bbte.idde.gvim2021.spring.dao.CommentDao;
import edu.bbte.idde.gvim2021.spring.model.Comment;

import java.util.Collection;

public class CommentDatabaseDao extends DatabaseDao<Comment> implements CommentDao {
    public CommentDatabaseDao() {
        super("COMMENTS", Comment.class);
    }

    @Override
    public Collection<Comment> findByAuthor(String author) {
        return executeQuery("author = " + author);
    }

    @Override
    public Collection<Comment> findByText(String text) {
        return executeQuery("text = " + text);
    }

    @Override
    public Collection<Comment> findByApartmentAdId(Long apartmentAdId) {
        return executeQuery("apartmentAdId = " + apartmentAdId);
    }
}
