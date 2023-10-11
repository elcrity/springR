package com.keduit.service;

import com.keduit.dto.TodoDto;
import com.keduit.model.TodoEntity;
import com.keduit.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public String testService(){
        TodoEntity todo = TodoEntity.builder()
                .title("Spring React Test1")
                .done(true)
                .build();
        todoRepository.save(todo);
        TodoEntity savedEntity = todoRepository.findById(todo.getId()).get();
        return savedEntity.getTitle();
    }

    private void validate(final TodoEntity entity){
        if(entity == null){
            log.warn("Entity is null");
            throw new RuntimeException("entity is null");
        }
        if(entity.getUserId() == null){
            log.warn("userId is null");
            throw new RuntimeException("userId is null");
        }
    }

    public List<TodoEntity> create(TodoEntity entity){
        validate(entity);
        todoRepository.save(entity);

        log.info("eneity id : {} is saved.", entity.getId());
        return todoRepository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> read(String userId){
        return todoRepository.findByUserId(userId);
    }

    public List<TodoEntity> readAll(){
        return todoRepository.findAll();
    }

    public List<TodoEntity> update(final TodoEntity entity){
        validate(entity);

        final Optional<TodoEntity> original = todoRepository.findById(entity.getId());
        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.getDone());
            todoRepository.save(todo);
        });

        return read(entity.getUserId());
    }
    public List<TodoEntity> delete(final TodoEntity entity){
        validate(entity);

        try{
            todoRepository.delete(entity);
        } catch (Exception e){
            log.error("delete error" + entity.getId(), e);
            throw new RuntimeException("delete error" + entity.getId());
        }

        return read(entity.getUserId());
    }
}
