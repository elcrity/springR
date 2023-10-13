package com.keduit.controller;


import com.keduit.dto.ResponseDto;
import com.keduit.dto.UserDto;
import com.keduit.model.UserEntity;
import com.keduit.security.TokenProvider;
import com.keduit.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){
        try {
            if (userDto == null || userDto.getPassword() == null) {
                throw new RuntimeException("사용자 혹은 비밀번호 입력 오류");
            }
            UserEntity user = UserEntity.builder()
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .build();

            UserEntity regUser = userService.create(user);
            UserDto responseUserDto = UserDto.builder()
                    .id(regUser.getId())
                    .username(regUser.getUsername())
                    .build();
            return ResponseEntity.ok().body(responseUserDto);
        } catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder()
                    .error((e.getMessage()))
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto){
        UserEntity user = userService.getByCredential(userDto.getUsername(), userDto.getPassword());

        if(user != null){
            final String token = tokenProvider.create(user);
            final UserDto responsUserDto = UserDto.builder()
                    .username(user.getUsername())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responsUserDto);
        } else {
            ResponseDto responseDto = ResponseDto.builder()
                    .error("로그인 에러")
                    .build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}
