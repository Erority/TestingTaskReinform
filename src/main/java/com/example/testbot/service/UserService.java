package com.example.testbot.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.example.testbot.model.request.CreateUserRequest;
import com.example.testbot.model.response.UserResponse;


public interface UserService {
    @NotNull
    List<UserResponse> findAll();

    @NotNull
    UserResponse findById(@NotNull Integer userId);

    @NotNull
    UserResponse createUser(@NotNull CreateUserRequest request);

    @NotNull
    UserResponse update(@NotNull Integer userId, @NotNull CreateUserRequest request);

    void delete(@NotNull Integer userId);    
}
