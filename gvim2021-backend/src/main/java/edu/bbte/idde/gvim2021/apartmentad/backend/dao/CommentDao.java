package edu.bbte.idde.gvim2021.apartmentad.backend.dao;

import edu.bbte.idde.gvim2021.apartmentad.backend.model.Comment;

import java.util.Collection;

public interface CommentDao extends Dao<Comment> {
    Collection<Comment> findByAuthor(String author);

    Collection<Comment> findByText(String text);

    Collection<Comment> findByApartmentAdId(Long apartmentAdId);
}
