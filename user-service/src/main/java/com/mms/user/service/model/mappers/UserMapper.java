package com.mms.user.service.model.mappers;

import com.mms.user.service.dtos.UserRequestDto;
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

    public User toUserRequest(UserRequestDto userRequestDto){
        return User.builder()
                .fullName(userRequestDto.fullName())
                .email(userRequestDto.getEmail())
                .accountLocked(userRequestDto.isAccountLocked())
                .password("1234")
                .enabled(userRequestDto.isEnabled())
                .build();
    }
}
