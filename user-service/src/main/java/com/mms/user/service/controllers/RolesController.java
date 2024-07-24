package com.mms.user.service.controllers;

import com.mms.user.service.dtos.RoleDto;
import com.mms.user.service.helper.ApiResponse;
import com.mms.user.service.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/roles")
@RestController
public class RolesController {
    @Autowired
    RoleService roleService;

    @GetMapping
    public ApiResponse getAllRoles(){
        return roleService.processGetAllRoles();
    }

    @GetMapping("/{id}")
    public ApiResponse getRoleById(@PathVariable("id") String id){
        return roleService.processGetOneRole(id);
    }

    @PostMapping
    public ApiResponse addRole(@RequestBody RoleDto roleDto){
        return roleService.processRoleCreation(roleDto);
    }

    @PutMapping("{id}")
    public ApiResponse updateRole(@PathVariable("id") String id, @RequestBody RoleDto roleDto){
        return roleService.processUpdateRole(id, roleDto);
    }
    @DeleteMapping("{id}")
    public ApiResponse deleteRole(@PathVariable("id") String id){
        return roleService.processDeleteRole(id);
    }
}
