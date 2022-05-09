package com.example.graphqltutorial.datafetchers;

import com.example.graphqltutorial.entities.TodoEntity;
import com.example.graphqltutorial.generated.types.Todo;
import com.example.graphqltutorial.generated.types.TodoInput;
import com.example.graphqltutorial.services.TodoService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DgsComponent
public class TodoDataFetcher {

    private final TodoService todoService;

    public TodoDataFetcher(TodoService todoService) {
        this.todoService = todoService;
    }

    @DgsQuery
    public List<Todo> todo() {
        return todoService.getTodos().stream().map(TodoDataFetcher::buildTodo).collect(Collectors.toList());
    }

    @DgsMutation
    public Todo addTodo(@InputArgument TodoInput todoInput) {
        TodoEntity todoEntity = todoService.addTodo(todoInput);
        return buildTodo(todoEntity);
    }

    @DgsMutation
    public Todo updateTodo(@InputArgument TodoInput todoInput) {
        TodoEntity todoEntity = todoService.updateTodo(UUID.fromString(todoInput.getTodo_id()), todoInput.getDescription());
        return buildTodo(todoEntity);
    }

    @DgsMutation
    public Todo deleteTodo(@InputArgument TodoInput todoInput) {
        TodoEntity todoEntity = todoService.deleteTodo(UUID.fromString(todoInput.getTodo_id()));
        return buildTodo(todoEntity);
    }

    private static Todo buildTodo(TodoEntity todo) {
        return Todo.newBuilder()
                .todo_id(todo.getTodo_id().toString())
                .description(todo.getDescription())
                .account_id(todo.getAccountId().toString())
                .build();
    }

}
