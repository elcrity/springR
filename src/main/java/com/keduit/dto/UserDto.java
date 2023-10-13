package com.keduit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String token;
    @ApiModelProperty(value = "아이디")
    private String username;
    @ApiModelProperty(value = "비밀번호")
    private String password;
    private String id;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
