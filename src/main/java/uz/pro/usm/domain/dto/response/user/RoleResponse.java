package uz.pro.usm.domain.dto.response.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uz.pro.usm.domain.entity.user.Permission;
import uz.pro.usm.domain.entity.user.Role;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
public class RoleResponse {

    @NotNull
    private final Long id;

    @NotNull
    private final String name;

    @NotNull
    private final List<String> permissions;

    public static RoleResponse from(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .permissions(
                        role.getPermissions().stream()
                                .map(Permission::getName)
                                .collect(Collectors.toList())
                )
                .build();
    }
}