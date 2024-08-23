package uz.pro.usm.domain.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pro.usm.domain.entity.user.RolePermission;
import uz.pro.usm.domain.entity.user.UserRole;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {
    private Long id;
    private String name;
    private List<String> permissions;

    public RoleResponse(UserRole role) {
        this.id = role.getId();
        this.name = role.getName();
        this.permissions = role.getPermissions().stream()
                .map(RolePermission::getName)
                .collect(Collectors.toList());
    }
}