package com.keduit.service;

import com.keduit.model.UserEntity;
import com.keduit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){
        if(userEntity == null || userEntity.getUsername() == null){
            throw new RuntimeException("invaild argument");
        }

        final String username = userEntity.getUsername();
        if(userRepository.existsByUsername(username)){
            log.warn("이미 존재하는 아이디입니다.", username);
            throw new RuntimeException(("이미 존재하는 아이디입니다."));
        }

        return userRepository.save(userEntity);
    }

    public UserEntity getByCredential(final String username,
                                      final String password,
                                      final PasswordEncoder passwordEncoder){
        final UserEntity oriUser =  userRepository.findByUsername(username);
        if (oriUser != null && passwordEncoder.matches(password, oriUser.getPassword()) ) {
            return oriUser;
        }
        return null;

    }



}
