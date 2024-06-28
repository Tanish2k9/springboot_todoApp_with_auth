package com.example.TodoApplication.service;

import com.example.TodoApplication.entity.Todo;
import com.example.TodoApplication.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public List<Todo> getAllTodos() {
        List<Todo> response = repository.findAll();
        return response;
    }
    public Optional<Todo> getTodo(Integer id){
        Optional<Todo> data = repository.findById(id);

        return data;
    }
    public Todo addTodo(Todo todo){
      Todo response =  repository.save(todo);
      return  response;
    }

    public String deleteTodo(Integer id){
        Optional<Todo> response = repository.findById(id);
        if(response.isPresent()){
            repository.deleteById(id);
            return "todo deleted";
        }
        return "todo not found";

    }

    public String updateTodo(Todo todo){
        Optional<Todo> response = repository.findById(todo.getId());
        if(response.isPresent()){
            Todo old = response.get();
            old.setWork(todo.getWork());
            repository.save(old);
            return "todo update";
        }
        return "todo not found";
    }


}
