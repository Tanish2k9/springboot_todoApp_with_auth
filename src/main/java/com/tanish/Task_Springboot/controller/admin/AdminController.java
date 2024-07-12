package com.tanish.Task_Springboot.controller.admin;

import com.tanish.Task_Springboot.dto.CommentDto;
import com.tanish.Task_Springboot.dto.TaskDto;
import com.tanish.Task_Springboot.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(adminService.getUsers());
    }

    @PostMapping("/task")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto){
        TaskDto createdTaskDto = adminService.createTask(taskDto);
        if(createdTaskDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDto);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(){
        return ResponseEntity.ok(adminService.getAllTasks());
    }
    @DeleteMapping("task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        adminService.deleteTask(id);
        return ResponseEntity.ok(null);
    }


    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getTask(id));
    }
    @PutMapping("/task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id,@RequestBody TaskDto taskDto){
        TaskDto updatedTask = adminService.updateTask(id,taskDto);
        if(updatedTask == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedTask);
    }
    @GetMapping("/task/search")
    public ResponseEntity<?> searchByTitle(@RequestParam("search") String search){
        return ResponseEntity.ok(adminService.searchTaskByTitle(search));
    }

    @PostMapping("/task/comment/{taskId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long taskId ,@RequestParam("content") String content){
        System.out.println(taskId);
        CommentDto createdCommentDto = adminService.createComment(taskId,content);
        if(createdCommentDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommentDto);
    }

    @GetMapping("/comments/{taskId}")
    public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable Long taskId){
        return ResponseEntity.ok(adminService.getCommentByTaskId(taskId));
    }

}
