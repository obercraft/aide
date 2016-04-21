package de.obercraft.aide.component;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.obercraft.aide.dto.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findBySubjectOrderByCreatedAsc(String subject);
    
    List<Comment> findByUserName(String userName);
}