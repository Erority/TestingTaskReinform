package com.example.testbot.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.testbot.service.UserService;
import com.example.testbot.model.request.CreateUserRequest;
import com.example.testbot.model.response.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
   private final UserService userService;

   //Get full list of users
   @GetMapping(produces = APPLICATION_JSON_VALUE)
   public List<UserResponse> findAll(){
       return userService.findAll();
   }

   //Get user by id 
   @GetMapping(value = "/{userId}",produces = APPLICATION_JSON_VALUE)
   public UserResponse findById(@PathVariable Integer userId){
       return userService.findById(userId);
   }

   //Create user
   @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
   public UserResponse create(@RequestBody CreateUserRequest request){ 
       return userService.createUser(request);
   }

   //Update user by id
   @PatchMapping(value = "/{userId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
   public UserResponse update(@PathVariable Integer userId, @RequestBody CreateUserRequest request){
       return userService.update(userId, request);
   }

   //Delete user by id
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @DeleteMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
   public void delete(@PathVariable Integer userId) {
       userService.delete(userId);
   }
}