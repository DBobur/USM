package uz.pro.usm.service.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pro.usm.domain.dto.request.user.RoleRequest;
import uz.pro.usm.domain.dto.response.user.RoleResponse;
import uz.pro.usm.domain.entity.user.Permission;
import uz.pro.usm.domain.entity.user.Role;
import uz.pro.usm.excaption.ResourceNotFoundException;
import uz.pro.usm.repository.user.PermissionRepository;
import uz.pro.usm.repository.user.RoleRepository;
import uz.pro.usm.service.BaseHelper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleResponse::from)
                .toList();
    }

    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role with id " + id + " not found"));
        return RoleResponse.from(role);
    }

    @Transactional
    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = Role.builder()
                .name(roleRequest.getName())
                .permissions(
                        permissionRepository.findAllById(roleRequest.getPermissionIds())
                )
                .build();
        Role savedRole = roleRepository.save(role);
        return RoleResponse.from(savedRole);
    }

    @Transactional
    public RoleResponse updateRole(Long id, RoleRequest roleRequest) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id " + id + " not found"));

        BaseHelper.updateIfPresent(roleRequest.getName(),role::setName);
        BaseHelper.updateIfPresent(
                roleRequest.getPermissionIds(),
                permissionIds -> {
                    List<Permission> permissions = validatePermissions(permissionIds);
                    role.setPermissions(permissions);
                }
        );
        Role updatedRole = roleRepository.save(role);

        return RoleResponse.from(updatedRole);
    }

    private List<Permission> validatePermissions(List<Long> permissionIds) {
        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        List<Long> foundIds = permissions.stream()
                .map(Permission::getId)
                .toList();
        List<Long> missingIds = permissionIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new IllegalArgumentException("Permissions with IDs " + missingIds + " not found");
        }

        return permissions;
    }

    @Transactional
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role with id " + id + " not found");
        }
        roleRepository.deleteById(id);
    }
}
