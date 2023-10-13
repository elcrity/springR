package com.keduit.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String token;
    private String username;
    private String password;
    private String id;
}
