package com.example.clanhubadv.service.validation;

import com.example.clanhubadv.entity.User;
import com.example.clanhubadv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidationService {

    private final UserRepository userRepository;

    public boolean userExists(String email) {
        Optional<User> existUser = userRepository.findByEmail(email);
        return existUser.isPresent();
    }

}
