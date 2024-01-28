package edu.bbte.idde.gvim2021.spring.dao.jpa;

import edu.bbte.idde.gvim2021.spring.dao.CommentDao;
import edu.bbte.idde.gvim2021.spring.model.BaseEntity;
import edu.bbte.idde.gvim2021.spring.model.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentJpaDao extends JpaRepository<Comment, BaseEntity>, CommentDao {
    @Override
    @Modifying
    @Transactional
    @Query("update Comment set author = :#{#comm.author} where id = :id")
    void update(@Param("id") Long id, @Param("comm") Comment comment);
}
