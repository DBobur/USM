package uz.pro.usm.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pro.usm.domain.dto.request.user.RoleRequest;
import uz.pro.usm.domain.dto.response.user.RoleResponse;
import uz.pro.usm.domain.entity.user.RolePermission;
import uz.pro.usm.domain.entity.user.UserRole;
import uz.pro.usm.repository.user.PermissionRepository;
import uz.pro.usm.repository.user.RoleRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository userRoleRepository;
    private final PermissionRepository rolePermissionRepository;

    public List<RoleResponse> getAllRoles() {
        return userRoleRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public RoleResponse getRoleById(Long id) {
        UserRole role = userRoleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        return mapToResponse(role);
    }

    public RoleResponse createRole(RoleRequest roleRequest) {
        UserRole role = new UserRole();
        role.setName(roleRequest.getName());
        role.setPermissions(rolePermissionRepository.findAllById(roleRequest.getPermissionIds()));
        UserRole savedRole = userRoleRepository.save(role);
        return mapToResponse(savedRole);
    }

    public RoleResponse updateRole(Long id, RoleRequest roleRequest) {
        UserRole role = userRoleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        role.setName(roleRequest.getName());
        role.setPermissions(rolePermissionRepository.findAllById(roleRequest.getPermissionIds()));
        UserRole updatedRole = userRoleRepository.save(role);
        return mapToResponse(updatedRole);
    }

    public void deleteRole(Long id) {
        userRoleRepository.deleteById(id);
    }

    public RoleResponse updateRolePermissions(Long roleId, List<String> permissionNames) {
        UserRole role = userRoleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        List<RolePermission> permissions = rolePermissionRepository.findByNameIn(permissionNames);
        if (permissions.isEmpty()) {
            throw new RuntimeException("Permissions not found");
        }

        role.setPermissions(new ArrayList<>(permissions));
        userRoleRepository.save(role);

        return new RoleResponse(role);
    }

    private RoleResponse mapToResponse(UserRole role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .permissions(role.getPermissions().stream().map(RolePermission::getName).toList())
                .build();
    }
}
