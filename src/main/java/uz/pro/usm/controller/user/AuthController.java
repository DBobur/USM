package uz.pro.usm.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pro.usm.domain.dto.request.user.LoginRequest;
import uz.pro.usm.domain.dto.request.user.RegisterRequest;
import uz.pro.usm.domain.dto.response.user.UserResponse;
import uz.pro.usm.service.user.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String token(@RequestBody LoginRequest request) {
       return authService.login(request);
    }

    @PreAuthorize("hasRole('SUPER')")
    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request){
        return authService.save(request);
    }
}
