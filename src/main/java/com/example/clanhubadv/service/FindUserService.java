package com.example.clanhubadv.service;

import com.example.clanhubadv.dto.responses.UserResponseDto;
import com.example.clanhubadv.entity.User;
import com.example.clanhubadv.repository.UserRepository;
import com.example.clanhubadv.service.converter.UserConverter;
import com.example.clanhubadv.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindUserService {
    private final UserRepository userRepository;
    private final UserConverter converter;


    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(manager -> converter.createDtoFromUser(manager))
                .toList();
    }

    public UserResponseDto findUserByEmail(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            UserResponseDto response = converter.createDtoFromUser(userOptional.get());
            return response;
        } else {
            throw new NotFoundException("User with email " + email + " not found");
        }
    }

    public List<User> findAllFullDetails() {
        return userRepository.findAll();
    }

    public boolean existsByEmail(String email) {
        try {
            return userRepository.existsByEmail(email);
        } catch (Exception e) {
            return false;
        }
    }


}