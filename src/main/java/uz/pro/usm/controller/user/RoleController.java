package uz.pro.usm.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pro.usm.domain.dto.request.user.RoleRequest;
import uz.pro.usm.domain.dto.response.user.RoleResponse;
import uz.pro.usm.service.user.RoleService;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        RoleResponse role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }

    @PreAuthorize("hasRole('SUPER')")
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        RoleResponse newRole = roleService.createRole(roleRequest);
        return ResponseEntity.ok(newRole);
    }
    @PreAuthorize("hasRole('SUPER')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @RequestBody RoleRequest roleRequest) {
        RoleResponse updatedRole = roleService.updateRole(id, roleRequest);
        return ResponseEntity.ok(updatedRole);
    }
    @PreAuthorize("hasRole('SUPER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}