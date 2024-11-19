package uz.pro.usm.service.user;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pro.usm.domain.dto.request.user.UserUpdateRequest;
import uz.pro.usm.domain.dto.response.user.UserResponse;
import uz.pro.usm.domain.entity.user.Role;
import uz.pro.usm.domain.entity.user.User;
import uz.pro.usm.excaption.ResourceNotFoundException;
import uz.pro.usm.repository.user.RoleRepository;
import uz.pro.usm.repository.user.UserRepository;
import uz.pro.usm.service.BaseHelper;
import uz.pro.usm.specification.UserSpecifications;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updateUser(Long id, @Valid UserUpdateRequest userUpdateRequest) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        BaseHelper.updateIfPresent(userUpdateRequest.getFullName(), existingUser::setFullName);
        BaseHelper.updateIfPresent(userUpdateRequest.getUsername(), existingUser::setUsername);
        BaseHelper.updateIfPresent(userUpdateRequest.getPassword(),
                password -> existingUser.setPassword(passwordEncoder.encode(password)));
        BaseHelper.updateIfPresent(userUpdateRequest.getEmail(), existingUser::setEmail);
        BaseHelper.updateIfPresent(userUpdateRequest.getNumber(), existingUser::setNumber);

        userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        userRepository.delete(user);
    }

    public Page<UserResponse> getAllUsers(String roleName, Pageable pageable) {
        Specification<User> spec = Specification.where(UserSpecifications.hasRoleName(roleName));
        Page<User> users = userRepository.findAll(spec, pageable);
        return users.map(UserResponse::from);
    }


    public UserResponse getUserById(Long id) {
        return UserResponse.from(userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + id + " not found")));
    }

    @Transactional
    public void updateUserRoles(Long userId, List<Long> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        if (roleIds == null || roleIds.isEmpty()) {
            throw new IllegalArgumentException("Role IDs must not be empty. Please provide valid role IDs.");
        }

        List<Role> roles = roleRepository.findAllById(roleIds);
        if (roles.size() != roleIds.size()) {
            List<Long> missingRoleIds = roleIds.stream()
                    .filter(id -> roles.stream().noneMatch(role -> role.getId().equals(id)))
                    .toList();
            throw new ResourceNotFoundException("Roles not found for IDs: " + missingRoleIds);
        }

        user.setRoles(new ArrayList<>(roles));
        userRepository.save(user);
    }

}
