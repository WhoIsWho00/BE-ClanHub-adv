package com.example.clanhubadv.service.converter;

import com.example.clanhubadv.dto.requests.signInUp.RegistrationRequest;
import com.example.clanhubadv.dto.responses.UserResponseDto;
import com.example.clanhubadv.entity.Role;
import com.example.clanhubadv.entity.User;
import com.example.clanhubadv.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {
    
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User createUserFromDto(RegistrationRequest request) {
        Role role = roleRepository.findByRoleName(request.getRole())
                .orElseGet(() -> roleRepository.save(new Role(request.getRole())));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setAvatarId(request.getAvatarId());
        user.setAge(request.getAge());
        
        return user;
    }

    public UserResponseDto createDtoFromUser(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setAvatarId(user.getAvatarId());
        dto.setAge(user.getAge());
        return dto;
    }
}