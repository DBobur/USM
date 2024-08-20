package uz.pro.usm.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pro.usm.domain.dto.request.user.LoginRequest;
import uz.pro.usm.domain.dto.request.user.RegisterRequest;
import uz.pro.usm.domain.dto.response.user.UserResponse;
import uz.pro.usm.domain.entity.user.User;
import uz.pro.usm.domain.mapper.UserMapper;
import uz.pro.usm.repository.RoleRepository;
import uz.pro.usm.repository.UserRepository;
import uz.pro.usm.security.JwtTokenUtil;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    private final PasswordEncoder passwordEncoder;
    public UserResponse save(RegisterRequest request){
        User user = User.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .roles(request.getRoleIds()
                        .stream()
                        .map(id -> roleRepository.findById(id).orElseThrow())
                        .collect(Collectors.toList()))
                .build();
        userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }

    public String login(LoginRequest request){
        String username = request.getUsername();
        String password = request.getPassword();

        var authentication = new UsernamePasswordAuthenticationToken(username,password);
        authenticationManager.authenticate(authentication);
       /* UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(!passwordEncoder.matches(password,userDetails.getPassword())) throw new BadCredentialsException("No password?");*/
        return jwtTokenUtil.generateToken(username);
    }
}
