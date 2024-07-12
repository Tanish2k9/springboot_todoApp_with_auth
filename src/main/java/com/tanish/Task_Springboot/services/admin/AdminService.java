package com.tanish.Task_Springboot.services.admin;

import com.tanish.Task_Springboot.dto.CommentDto;
import com.tanish.Task_Springboot.dto.TaskDto;
import com.tanish.Task_Springboot.dto.UserDto;
import com.tanish.Task_Springboot.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<UserDto> getUsers();
    TaskDto createTask(TaskDto taskDto);
    List<TaskDto> getAllTasks();
    void deleteTask(Long id);

    TaskDto getTask(Long id);
    TaskDto updateTask(Long id,TaskDto taskDto);
    List<TaskDto> searchTaskByTitle(String title);
    CommentDto createComment(Long taskId, String content);
    List<CommentDto> getCommentByTaskId(Long taskId);
}
