package uz.pro.usm.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pro.usm.domain.dto.request.user.UserUpdateRequest;
import uz.pro.usm.domain.entity.user.User;
import uz.pro.usm.repository.UserRepository;

import java.util.function.Consumer;

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

    private void updateIfPresent(String newValue, Consumer<String> setter) {
        if (newValue != null && !newValue.isEmpty()) {
            setter.accept(newValue);
        }
    }
}
