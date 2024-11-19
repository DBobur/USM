package uz.pro.usm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pro.usm.domain.entity.user.User;
import uz.pro.usm.repository.user.UserRepository;

import java.util.Collections;

@SpringBootApplication
public class UsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsmApplication.class, args);
    }
}
