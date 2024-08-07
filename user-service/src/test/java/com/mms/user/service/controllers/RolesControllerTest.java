package com.mms.user.service.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RolesController.class)
@ExtendWith(MockitoExtension.class)
class RolesControllerTest {

    /*
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @InjectMocks
    private RolesController rolesController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllRoles() throws Exception {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(200);
        // set other fields of apiResponse if necessary

        when(roleService.processGetAllRoles()).thenReturn(apiResponse);

        mockMvc.perform(get("/api/v1/roles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testGetRoleById() throws Exception {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(200);
        // set other fields of apiResponse if necessary

        when(roleService.processGetOneRole(any(String.class))).thenReturn(apiResponse);

        mockMvc.perform(get("/api/v1/roles/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testAddRole() throws Exception {
        RoleDto roleDto = new RoleDto();
        // populate roleDto with necessary fields

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(201);
        // set other fields of apiResponse if necessary

        when(roleService.processRoleCreation(any(RoleDto.class))).thenReturn(apiResponse);

        mockMvc.perform(post("/api/v1/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201));
    }

    @Test
    void testUpdateRole() throws Exception {
        RoleDto roleDto = new RoleDto();
        // populate roleDto with necessary fields

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(200);
        // set other fields of apiResponse if necessary

        when(roleService.processUpdateRole(any(String.class), any(RoleDto.class))).thenReturn(apiResponse);

        mockMvc.perform(put("/api/v1/roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testDeleteRole() throws Exception {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(200);
        // set other fields of apiResponse if necessary

        when(roleService.processDeleteRole(any(String.class))).thenReturn(apiResponse);

        mockMvc.perform(delete("/api/v1/roles/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }
    * */
}