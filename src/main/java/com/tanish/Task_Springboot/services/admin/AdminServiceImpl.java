package com.tanish.Task_Springboot.services.admin;

import com.tanish.Task_Springboot.dto.CommentDto;
import com.tanish.Task_Springboot.dto.TaskDto;
import com.tanish.Task_Springboot.dto.UserDto;
import com.tanish.Task_Springboot.entities.Comment;
import com.tanish.Task_Springboot.entities.Task;
import com.tanish.Task_Springboot.entities.User;
import com.tanish.Task_Springboot.enums.TaskStatus;
import com.tanish.Task_Springboot.enums.UserRole;
import com.tanish.Task_Springboot.repositories.CommentRepository;
import com.tanish.Task_Springboot.repositories.TaskRepository;
import com.tanish.Task_Springboot.repositories.UserRepository;
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
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Optional<User> optionalUser = userRepository.findById(taskDto.getEmployeeId());
        if(optionalUser.isPresent()){
            Task task = new Task();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setPriority(taskDto.getPriority());
            task.setDueDate(taskDto.getDueDate());
            task.setTaskStatus(TaskStatus.INPROGRESS);
            task.setUser(optionalUser.get());
            return taskRepository.save(task).getTaskDto();
        }
        return null;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .map(Task::getTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    public TaskDto getTask(Long id){
       Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(Task::getTaskDto).orElse(null);
    }
    public TaskDto updateTask(Long id, TaskDto taskDto){
        Optional<Task> optionalTask = taskRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(taskDto.getEmployeeId());
        if(optionalTask.isPresent() && optionalUser.isPresent()){
            Task existingTask = optionalTask.get();
            existingTask.setTitle(taskDto.getTitle());
            existingTask.setDescription(taskDto.getDescription());
            existingTask.setDueDate(taskDto.getDueDate());
            existingTask.setPriority(taskDto.getPriority());
            existingTask.setTaskStatus(mapStringToTaskStatus(String.valueOf(taskDto.getTaskStatus())));
            return taskRepository.save(existingTask).getTaskDto();
        }
        return null;
    }

    @Override
    public List<TaskDto> searchTaskByTitle(String title) {
        return taskRepository.findAllByTitleContaining(title)
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate))
                .map(Task::getTaskDto)
                .collect(Collectors.toList());
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
