package uz.pro.usm.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pro.usm.domain.dto.request.user.UserUpdateRequest;
import uz.pro.usm.domain.dto.response.user.UserResponse;
import uz.pro.usm.domain.entity.user.User;
import uz.pro.usm.repository.user.UserRepository;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        updateIfPresent(userUpdateRequest.getFullName(), existingUser::setFullName);
        updateIfPresent(userUpdateRequest.getUsername(), existingUser::setUsername);
        updateIfPresent(userUpdateRequest.getPassword(), existingUser::setPassword);
        updateIfPresent(userUpdateRequest.getEmail(), existingUser::setEmail);
        updateIfPresent(userUpdateRequest.getNumber(), existingUser::setNumber);

        userRepository.save(existingUser);
    }

    public void deleteUser(Long id){
        userRepository.delete(userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        ));
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }


    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        return mapToUserResponse(user);
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .number(user.getNumber())
                .roles(user.getRoles().stream()
                        .map(role -> role.getName())
                        .collect(Collectors.toList()))
                .createdDate(user.getCreatedDate().toString())
                .updatedDate(user.getUpdatedDate().toString())
                .build();
    }

    private void updateIfPresent(String newValue, Consumer<String> setter) {
        if (newValue != null && !newValue.isEmpty()) {
            setter.accept(newValue);
        }
    }
}
