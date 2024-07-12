package com.tanish.Task_Springboot.repositories;

import com.tanish.Task_Springboot.dto.TaskDto;
import com.tanish.Task_Springboot.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByTitleContaining(String title);


    List<Task> findAllByUserId(Long id);
}
