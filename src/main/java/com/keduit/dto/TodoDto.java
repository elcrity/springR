package com.keduit.dto;

import com.keduit.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private String id;
    private String title;
    private String userId;
    private boolean done;

    public TodoDto(final TodoEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.userId = entity.getUserId();
        this.done = entity.getDone();
    }

    public static TodoEntity toEntity(final TodoDto dto){
        return TodoEntity.builder()
                .title(dto.getTitle())
                .id(dto.getId())
                .userId(dto.getUserId())
                .done(dto.isDone())
                .build();
    }

}
