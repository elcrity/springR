package com.keduit.controller;

import com.keduit.dto.ResponseDto;
import com.keduit.dto.TodoDto;
import com.keduit.model.TodoEntity;
import com.keduit.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    String tempUser = "TempId";

//    @GetMapping("/test")
//    public ResponseEntity<?> testTodo(){
//        String str = todoService.testService();
//
//        List<String> list = new ArrayList<>();
//        list.add(str);
//
//        ResponseDto<String> response = ResponseDto.<String>builder()
//                .data(list)
//                .build();
//        return ResponseEntity.ok().body(response);
//    }
    @GetMapping
    public ResponseEntity<?> readAllTodo(){
        List<TodoEntity> list = todoService.readAll();
        ResponseDto<TodoEntity> response = ResponseDto.<TodoEntity>builder()
                .data(list)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/test")
    public ResponseEntity<?> readTodo(@RequestBody TodoDto dto){
        TodoEntity entity = TodoDto.toEntity(dto);

        List<TodoEntity> readEntity = todoService.read(entity.getUserId());

        ResponseDto<TodoEntity> response = ResponseDto.<TodoEntity>builder()
                .data(readEntity)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/test")
    public ResponseEntity<?> createTodo(@RequestBody TodoDto dto){
        try {
            TodoEntity entity = TodoDto.toEntity(dto);
            entity.setId(null);

            //서비스로부터 엔티티 List를 가져옴
            List<TodoEntity> entities = todoService.create(entity);
            //엔티티 List 를 TodoDto리스트로 변환
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

            //변환된 TodoDto 리스트를 이용해 ResponseDto를 최적화
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                    .data(dtos)
                    .build();
            //ResponseDto를 반환
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            //예외 발생시 DTO대신 error에 메세지를 넣어 리턴
            String error = e.getMessage();
            ResponseDto<TodoDto> responseDto = ResponseDto.<TodoDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PutMapping("/test")
    public ResponseEntity<?> updateTodo(@RequestBody TodoDto dto){
        TodoEntity entity = TodoDto.toEntity(dto);

        List<TodoEntity> entities = todoService.update(entity);

        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                .data(dtos)
                .build();

        return ResponseEntity.ok().body(response);

    }

    @DeleteMapping("/test")
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDto dto){

        try {
            TodoEntity entity = TodoDto.toEntity(dto);
            entity.setUserId(dto.getUserId());

            List<TodoEntity> entities = todoService.delete(entity);

            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                    .data(dtos)
                    .build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<TodoDto> response =  ResponseDto.<TodoDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

}
