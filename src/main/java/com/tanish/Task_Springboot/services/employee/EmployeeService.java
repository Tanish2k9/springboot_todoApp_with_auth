package com.tanish.Task_Springboot.services.employee;

import com.tanish.Task_Springboot.dto.CommentDto;
import com.tanish.Task_Springboot.dto.TaskDto;

import java.util.List;

public interface EmployeeService {
    List<TaskDto> getTaskByUserId();
    TaskDto updateTask(Long id, String status);
    TaskDto getTask(Long id);
    CommentDto createComment(Long taskId, String content);
    List<CommentDto> getCommentByTaskId(Long taskId);

}
