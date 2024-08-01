package com.mms.user.service.model.mappers;

import com.mms.user.service.dtos.UserResponseDto;
import com.mms.user.service.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserResponseDto toUserResponse(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .fullName(user.fullName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .accountLocked(user.isAccountLocked())
                .enabled(user.isEnabled())
                .build();
    }
}
