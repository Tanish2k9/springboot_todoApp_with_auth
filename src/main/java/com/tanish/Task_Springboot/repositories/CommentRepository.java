package com.tanish.Task_Springboot.repositories;

import com.tanish.Task_Springboot.dto.CommentDto;
import com.tanish.Task_Springboot.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByTaskId(Long taskId);
}
