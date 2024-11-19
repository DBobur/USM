package uz.pro.usm.service.user;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pro.usm.domain.dto.request.user.LoginRequest;
import uz.pro.usm.domain.dto.request.user.UserRequest;
import uz.pro.usm.domain.dto.response.user.UserResponse;
import uz.pro.usm.domain.entity.user.Role;
import uz.pro.usm.domain.entity.user.User;
import uz.pro.usm.excaption.ResourceNotFoundException;
import uz.pro.usm.repository.user.RoleRepository;
import uz.pro.usm.repository.user.UserRepository;
import uz.pro.usm.security.CustomUserDetailsService;
import uz.pro.usm.security.JwtTokenUtil;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService service;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse save(@Valid UserRequest request) {
        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));

        User user = User.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .number(request.getNumber())
                .address(request.getAddress())
                .dateOfBirth(LocalDate.parse(request.getDateOfBirth()))
                .roles(List.of(defaultRole))
                .build();

        userRepository.save(user);
        return UserResponse.from(user);
    }

    public String login(@Valid LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        UserDetails userDetails = service.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        return jwtTokenUtil.generateToken(username);
    }
}
