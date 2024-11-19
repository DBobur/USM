package uz.pro.usm.domain.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pro.usm.domain.entity.user.Role;
import uz.pro.usm.domain.entity.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String fullName;
    private String username;
    private String email;
    private String number;
    private String address;
    private LocalDate dateOfBirth;
    private List<String> roles;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdatedDate())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .number(user.getNumber())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()))
                .build();
    }
}

