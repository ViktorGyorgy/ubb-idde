package edu.bbte.idde.gvim2021.apartmentad.backend.dao.memory;

import edu.bbte.idde.gvim2021.apartmentad.backend.dao.CommentDao;
import edu.bbte.idde.gvim2021.apartmentad.backend.model.Comment;

import java.util.Collection;
import java.util.stream.Collectors;

public class CommentMemoryDao extends MemoryDao<Comment> implements CommentDao {
    @Override
    public Collection<Comment> findByAuthor(String author) {
        return entities.values().stream()
                .filter(comment -> author.equals(comment.getAuthor()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Comment> findByText(String text) {
        return entities.values().stream()
                .filter(comment -> text.equals(comment.getText()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Comment> findByApartmentAdId(Long apartmentAdId) {
        return entities.values().stream()
                .filter(comment -> apartmentAdId.equals(comment.getApartmentAdId()))
                .collect(Collectors.toList());
    }
}
