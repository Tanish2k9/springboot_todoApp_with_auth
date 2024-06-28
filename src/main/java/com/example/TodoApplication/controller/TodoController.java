package com.example.TodoApplication.controller;

import com.example.TodoApplication.entity.Todo;
import com.example.TodoApplication.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/todo/{id}")
    public ResponseEntity<?> getTodo(@PathVariable Integer id){
        Optional<Todo> response = todoService.getTodo(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/todos")
    public ResponseEntity<?> getTodos(){
        List<Todo> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @PostMapping("/todo")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo){
        Todo response = todoService.addTodo(todo);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @PutMapping("/todo")
    public ResponseEntity<?> updateTodo(@RequestBody Todo todo){
        String response = todoService.updateTodo(todo);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/todo/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Integer id){
        String response = todoService.deleteTodo(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
