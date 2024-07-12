package com.tanish.Task_Springboot.services.employee;

import com.tanish.Task_Springboot.dto.CommentDto;
import com.tanish.Task_Springboot.dto.TaskDto;
import com.tanish.Task_Springboot.entities.Comment;
import com.tanish.Task_Springboot.entities.Task;
import com.tanish.Task_Springboot.entities.User;
import com.tanish.Task_Springboot.enums.TaskStatus;
import com.tanish.Task_Springboot.repositories.CommentRepository;
import com.tanish.Task_Springboot.repositories.TaskRepository;
import com.tanish.Task_Springboot.utils.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    private final TaskRepository taskRepository;
    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;
    @Override
    public List<TaskDto> getTaskByUserId() {
        User user = jwtUtil.getLoggedInUser();
        if(user != null) {
            return taskRepository.findAllByUserId(user.getId())
                    .stream()
                    .sorted(Comparator.comparing(Task::getDueDate).reversed())
                    .map(Task::getTaskDto)
                    .collect(Collectors.toList());
        }
        throw new EntityNotFoundException("User not found");
    }

    @Override
    public TaskDto updateTask(Long id, String status) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task existingTask = optionalTask.get();
            existingTask.setTaskStatus(mapStringToTaskStatus(status));
           return taskRepository.save(existingTask).getTaskDto();
        }
        throw new EntityNotFoundException("task not found");
    }


    public TaskDto getTask(Long id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(Task::getTaskDto).orElse(null);
    }

    @Override
    public CommentDto createComment(Long taskId, String content) {
        User user = jwtUtil.getLoggedInUser();
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if((optionalTask.isPresent() && user != null)){
            Comment comment = new Comment();
            comment.setCreatedAt(new Date());
            comment.setContent(content);
            comment.setTask(optionalTask.get());
            comment.setUser(user);
            return commentRepository.save(comment).getCommentDto();
        }

        throw new EntityNotFoundException("user or task not found");
    }

    @Override
    public List<CommentDto> getCommentByTaskId(Long taskId) {
        return commentRepository.findAllByTaskId(taskId)
                .stream()
                .map(Comment::getCommentDto)
                .collect(Collectors.toList());
    }
    private TaskStatus mapStringToTaskStatus(String status){
        return switch (status){
            case "PENDING" ->TaskStatus.PENDING;
            case "INPROGRESS" ->TaskStatus.INPROGRESS;
            case "COMPLETED" ->TaskStatus.COMPLETED;
            case "DEFERRED" ->TaskStatus.DEFERRED;
            default -> TaskStatus.CANCELED;

        };
    }

}
