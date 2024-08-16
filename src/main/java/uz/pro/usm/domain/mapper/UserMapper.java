package uz.pro.usm.domain.mapper;

import org.springframework.stereotype.Component;
import uz.pro.usm.domain.dto.response.UserResponse;
import uz.pro.usm.domain.entity.user.User;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .number(user.getNumber())
                .roles(user.getRoles().stream()
                        .map(role -> role.getName())
                        .toList())
                .createdDate(user.getCreatedDate().toString())
                .updatedDate(user.getUpdatedDate().toString())
                .build();
    }
}
