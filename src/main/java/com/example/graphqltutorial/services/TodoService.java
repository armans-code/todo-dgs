package com.example.graphqltutorial.services;


import com.example.graphqltutorial.entities.AccountEntity;
import com.example.graphqltutorial.entities.TodoEntity;
import com.example.graphqltutorial.generated.types.TodoInput;
import com.example.graphqltutorial.repositories.AccountRepository;
import com.example.graphqltutorial.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository, AccountRepository accountRepository) {
        this.todoRepository = todoRepository;
        this.accountRepository = accountRepository;
    }

    public List<TodoEntity> getTodos() {
        return todoRepository.findAll();
    }

    public TodoEntity addTodo(TodoInput todoInput) {
        AccountEntity accountEntity = accountRepository.findById(UUID.fromString(todoInput.getAccount_id()))
                .orElseThrow(() -> new IllegalStateException(
                        "account with id + " + todoInput.getAccount_id() + " not found"
                ));
        TodoEntity todo = new TodoEntity(todoInput.getDescription(), accountEntity);

        return todoRepository.save(todo);
    }

    @Transactional
    public TodoEntity updateTodo(UUID todo_id, String description) {
        TodoEntity todo = todoRepository.findById(todo_id).orElseThrow(() -> new IllegalStateException(
                "todo with id " + todo_id + " not found"
        ));

        todo.setDescription(description);
        return todo;
    }

    public TodoEntity deleteTodo(UUID todo_id) {
        TodoEntity todo = todoRepository.findById(todo_id).orElseThrow(() -> new IllegalStateException("" +
                "todo with id " + todo_id + " not found"
        ));
        todoRepository.delete(todo);
        return todo;
    }

}
