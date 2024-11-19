package uz.pro.usm.controller.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pro.usm.domain.dto.request.user.UserUpdateRequest;
import uz.pro.usm.domain.dto.response.user.UserResponse;
import uz.pro.usm.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(id, userUpdateRequest);
        return ResponseEntity.ok("User updated successfully");
    }

    @PreAuthorize("hasAnyAuthority('DELETE_USER')")
    //@PreAuthorize("hasRole('SUPER')")
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam(name = "id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('SUPER')")
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(name = "roleName", required = false) String roleName,
            @PageableDefault(size = 10, sort = "createdDate") Pageable pageable) {

        log.info("User {} requested getAllUsers with roleName={} and pageable={}",
                SecurityContextHolder.getContext().getAuthentication().getName(), roleName, pageable);

        Page<UserResponse> users = userService.getAllUsers(roleName, pageable);
        return ResponseEntity.ok(users);
    }




    @PreAuthorize("hasAnyAuthority('GET_USER_BY_ID')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_USER_ROLES')")
    @PutMapping("/{id}/roles")
    public ResponseEntity<String> updateUserRoles(
            @PathVariable Long id,
            @RequestBody List<Long> roleIds) {
        userService.updateUserRoles(id, roleIds);
        return ResponseEntity.ok("User roles updated successfully");
    }

}
