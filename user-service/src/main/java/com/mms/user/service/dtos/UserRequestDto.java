package com.mms.user.service.dtos;

import com.mms.user.service.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    private String fullName;
    @Column(unique = true)
    private String email;
    private boolean accountLocked;
    private boolean enabled;
    @ManyToMany(fetch = EAGER)
    private int role;

    public String fullName() {
        return fullName;
    }
}
