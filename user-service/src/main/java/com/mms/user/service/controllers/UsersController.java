package com.mms.user.service.controllers;

import com.mms.user.service.dtos.RegistrationDto;
import com.mms.user.service.helper.ApiResponse;
import com.mms.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/users")
@RestController
public class UsersController {
    @Autowired
    UserService userService;

    @GetMapping
    public ApiResponse getAllUser(){
        return userService.processGetAllUsers();
    }


    @GetMapping("/{id}")
    public ApiResponse getUserById(@PathVariable("id") String id){
        return userService.processGetOneUser(id);
    }

    @PostMapping
    public ApiResponse<String> addUser(@RequestBody RegistrationDto registrationDto){
        return userService.processUserCreation(registrationDto);
    }

    @PutMapping("{id}")
    public ApiResponse<String> updateUser(@PathVariable("id") String id, @RequestBody RegistrationDto registrationDto){
        return userService.processUpdateUser(id, registrationDto);
    }

    @PutMapping("/change-account-status/{id}")
    public ApiResponse<String> accountStatusChange(@PathVariable("id") String id, @PathVariable("status") String status){
        return userService.processAccountStatusChange(id, status);
    }

    @DeleteMapping("{id}")
    public ApiResponse<String> deleteUser(@PathVariable("id") String id){
        return userService.processGetOneUser(id);
    }
}
