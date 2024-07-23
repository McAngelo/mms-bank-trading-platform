package com.mms.user.service.controllers;

import com.mms.user.service.helper.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/roles")
@RestController
public class RolesController {
    ApiResponse<String> apiResponse = new ApiResponse<>();

    @GetMapping
    public ApiResponse<String> getAllRoles(){
        apiResponse.setMessage("Get All Roles, successfully");
        apiResponse.setStatus(200);
        return apiResponse;
    }


    @GetMapping("/{id}")
    public ApiResponse<String> getRoleById(@PathVariable("id") String id){
        apiResponse.setMessage("Get only one role details, successfully");
        apiResponse.setStatus(200);
        return apiResponse;
    }

    @PostMapping
    public ApiResponse<String> addRole(){
        apiResponse.setMessage("Create a role record, successfully");
        apiResponse.setStatus(200);
        return apiResponse;
    }

    @PutMapping("{id}")
    public ApiResponse<String> updateRole(@PathVariable("id") String id){
        System.out.println(id);
        apiResponse.setMessage("Update role details, successfully");
        apiResponse.setStatus(200);
        return apiResponse;
    }
    @DeleteMapping("{id}")
    public ApiResponse<String> deleteRole(@PathVariable("id") String id){
        System.out.println(id);
        apiResponse.setMessage("Delete role Account, successfully");
        apiResponse.setStatus(200);
        return apiResponse;
    }
}
