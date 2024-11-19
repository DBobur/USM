package uz.pro.usm.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pro.usm.domain.entity.user.Permission;
import uz.pro.usm.domain.dto.request.user.PermissionRequest;
import uz.pro.usm.domain.dto.response.user.PermissionResponse;
import uz.pro.usm.excaption.ResourceNotFoundException;
import uz.pro.usm.repository.user.PermissionRepository;
import uz.pro.usm.service.BaseHelper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(PermissionResponse::from)
                .collect(Collectors.toList());
    }

    public PermissionResponse getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission with id " + id + " not found"));
        return PermissionResponse.from(permission);
    }

    @Transactional
    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = Permission.builder()
                .name(permissionRequest.getName())
                .build();
        Permission savedPermission = permissionRepository.save(permission);
        return PermissionResponse.from(savedPermission);
    }

    @Transactional
    public PermissionResponse updatePermission(Long id, PermissionRequest permissionRequest) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission with id " + id + " not found"));

        BaseHelper.updateIfPresent(permissionRequest.getName(),permission::setName);
        BaseHelper.updateIfPresent(permissionRequest.getDescription(),permission::setDescription);

        Permission updatedPermission = permissionRepository.save(permission);
        return PermissionResponse.from(updatedPermission);
    }

    @Transactional
    public void deletePermission(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Permission with id " + id + " not found");
        }
        permissionRepository.deleteById(id);
    }
}
