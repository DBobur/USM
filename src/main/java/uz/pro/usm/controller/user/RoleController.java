package uz.pro.usm.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    @Operation(summary = "Fayl yuklash va role yaratish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fayl muvaffaqiyatli yuklandi",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Yuklashda xatolik",
                    content = @Content)
    })
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> uploadFile(
            @RequestPart ("file") MultipartFile file,
            @RequestParam("roleRequest") RoleRequest roleRequest) {

        // Faylni qayta ishlash
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Fayl bo'sh.");
        }

        // Faylni saqlash yoki boshqa ishlarni bajaring
        String fileName = file.getOriginalFilename();

        // Rol yaratish jarayoni
        RoleResponse newRole = roleService.createRole(roleRequest);

        // Muvaffaqiyatli natija
        return ResponseEntity.ok("Fayl muvaffaqiyatli yuklandi: " + fileName + ", Rol: " + newRole);
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

    @PreAuthorize("hasRole('SUPER')")
    @PutMapping("/{id}/permissions")
    public ResponseEntity<RoleResponse> updateRolePermissions(
            @PathVariable Long id,
            @RequestBody List<String> permissions) {
        RoleResponse updatedRole = roleService.updateRolePermissions(id, permissions);
        return ResponseEntity.ok(updatedRole);
    }

}