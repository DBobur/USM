package uz.pro.usm.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pro.usm.domain.dto.request.user.PermissionRequest;
import uz.pro.usm.domain.dto.response.user.PermissionResponse;
import uz.pro.usm.service.user.PermissionService;

import java.util.List;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService rolePermissionService;

    //Hamma permissionlarni ko'rish
    @GetMapping
   //@PreAuthorize("hasAnyAuthority('FULLY','GE_ALL_PERMISSION')")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','DIRECTOR','MANAGER','MENTOR')")
    public ResponseEntity<List<PermissionResponse>> getAllPermissions() {
        List<PermissionResponse> permissions = rolePermissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }
    //Aynan id bo'yicha tegishli permissionni ko'rish
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER','DIRECTOR','MANAGER','MENTOR')")
    public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable Long id) {
        PermissionResponse permission = rolePermissionService.getPermissionById(id);
        return ResponseEntity.ok(permission);
    }
    //Yangi permission yaratish
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER')")
    public ResponseEntity<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        PermissionResponse newPermission = rolePermissionService.createPermission(permissionRequest);
        return ResponseEntity.ok(newPermission);
    }
    //Bazada bor permissionni update qilish
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER')")
    public ResponseEntity<PermissionResponse> updatePermission(@PathVariable Long id, @RequestBody PermissionRequest permissionRequest) {
        PermissionResponse updatedPermission = rolePermissionService.updatePermission(id, permissionRequest);
        return ResponseEntity.ok(updatedPermission);
    }
    //Bazada bor permissionni o'chirish
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER')")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        rolePermissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}
