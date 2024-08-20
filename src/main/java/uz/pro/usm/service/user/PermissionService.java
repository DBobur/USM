package uz.pro.usm.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pro.usm.domain.entity.user.RolePermission;
import uz.pro.usm.domain.dto.request.user.PermissionRequest;
import uz.pro.usm.domain.dto.response.user.PermissionResponse;
import uz.pro.usm.repository.user.PermissionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository rolePermissionRepository;

    public List<PermissionResponse> getAllPermissions() {
        return rolePermissionRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PermissionResponse getPermissionById(Long id) {
        RolePermission permission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
        return mapToResponse(permission);
    }

    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        RolePermission permission = RolePermission.builder()
                .name(permissionRequest.getName())
                .build();
        RolePermission savedPermission = rolePermissionRepository.save(permission);
        return mapToResponse(savedPermission);
    }

    public PermissionResponse updatePermission(Long id, PermissionRequest permissionRequest) {
        RolePermission permission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
        permission.setName(permissionRequest.getName());
        RolePermission updatedPermission = rolePermissionRepository.save(permission);
        return mapToResponse(updatedPermission);
    }

    public void deletePermission(Long id) {
        rolePermissionRepository.deleteById(id);
    }

    private PermissionResponse mapToResponse(RolePermission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .build();
    }
}
