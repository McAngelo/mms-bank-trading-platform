package com.mms.user.service.controllers;

import com.mms.user.service.dtos.RegistrationRequestDto;
import com.mms.user.service.helper.ApiResponse;
import com.mms.user.service.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Tag(name = "Users")
public class UsersController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size){
        var result = userService.processGetAllUsers(page, size);

        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") int id){
        var result = userService.processGetOneUser(id);

        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @PostMapping
    public ApiResponse<String> addUser(@RequestBody RegistrationRequestDto registrationDto){
        return userService.processUserCreation(registrationDto);
    }

    @PutMapping("{id}")
    public ApiResponse<String> updateUser(@PathVariable("id") String id, @RequestBody RegistrationRequestDto registrationDto){
        return userService.processUpdateUser(id, registrationDto);
    }

    @PutMapping("/change-account-status/{id}")
    public ApiResponse<String> accountStatusChange(@PathVariable("id") String id, @PathVariable("status") String status){
        return userService.processAccountStatusChange(id, status);
    }

    @DeleteMapping("{id}")
    public ApiResponse<String> deleteUser(@PathVariable("id") int id){
        return userService.processGetOneUser(id);
    }
}
