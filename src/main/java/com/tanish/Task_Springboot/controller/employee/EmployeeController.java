package com.tanish.Task_Springboot.controller.employee;

import com.tanish.Task_Springboot.dto.CommentDto;
import com.tanish.Task_Springboot.dto.TaskDto;
import com.tanish.Task_Springboot.services.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("/tasks")
    public ResponseEntity<?> getTaskByUserId(){
        return ResponseEntity.ok(employeeService.getTaskByUserId());
    }

    @GetMapping("/task/{id}/{status}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @PathVariable String status){
        TaskDto taskDto = employeeService.updateTask(id,status);
        if(taskDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(taskDto);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.getTask(id));
    }

    @PostMapping("/task/comment/{taskId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long taskId , @RequestParam("content") String content){
        System.out.println(taskId);
        CommentDto createdCommentDto = employeeService.createComment(taskId,content);
        if(createdCommentDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommentDto);
    }

    @GetMapping("/comments/{taskId}")
    public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable Long taskId){
        return ResponseEntity.ok(employeeService.getCommentByTaskId(taskId));
    }
}
