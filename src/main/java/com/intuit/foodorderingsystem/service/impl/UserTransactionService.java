package com.intuit.foodorderingsystem.service.impl;

import com.intuit.foodorderingsystem.builder.UsersEntityBuilderFactory;
import com.intuit.foodorderingsystem.entity.UserAuthenticationEntity;
import com.intuit.foodorderingsystem.entity.UsersEntity;
import com.intuit.foodorderingsystem.model.request.CreateUserRequest;
import com.intuit.foodorderingsystem.repository.UserAuthenticationRepository;
import com.intuit.foodorderingsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserTransactionService {

    private final UserRepository userRepository;
    private final UserAuthenticationRepository userAuthenticationRepository;

    @Transactional
    public UsersEntity createUser(CreateUserRequest createUserRequest) {
        UsersEntity usersEntity = UsersEntityBuilderFactory.build(createUserRequest);
        usersEntity = userRepository.save(usersEntity);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(createUserRequest.getPassword());

        UserAuthenticationEntity userAuthenticationEntity = UserAuthenticationEntity.builder()
                .id(usersEntity.getId())
                .encryptedPassword(encodedPassword)
                .build();
        userAuthenticationRepository.save(userAuthenticationEntity);

        return usersEntity;
    }
}
