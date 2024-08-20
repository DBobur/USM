package uz.pro.usm.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request){
        return authService.save(request);
    }
}
