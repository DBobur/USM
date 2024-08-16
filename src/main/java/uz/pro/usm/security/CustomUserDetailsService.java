package uz.pro.usm.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pro.usm.domain.entity.user.User;
import uz.pro.usm.domain.entity.user.UserRole;
import uz.pro.usm.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,true,true,true,
                getAuthorities(user.getRoles())
        );

    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<UserRole> roles) {
        return roles.stream().flatMap(userRole -> userRole.getPermissions().stream())
                .map(rolePermission -> new SimpleGrantedAuthority(rolePermission.getName()))
                .collect(Collectors.toList());
    }
}
