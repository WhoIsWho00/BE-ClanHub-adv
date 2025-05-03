package com.example.clanhubadv.service;

import com.example.clanhubadv.Other.IPAdressUtil.IpAddressUtil;
import com.example.clanhubadv.dto.requests.signInUp.RegistrationRequest;
import com.example.clanhubadv.dto.requests.UpdateProfileRequest;
import com.example.clanhubadv.dto.responses.UserResponseDto;
import com.example.clanhubadv.entity.User;
import com.example.clanhubadv.entity.UserRegistrationLog;
import com.example.clanhubadv.repository.UserRegistrationLogRepository;
import com.example.clanhubadv.repository.UserRepository;
import com.example.clanhubadv.service.converter.UserConverter;
import com.example.clanhubadv.service.exception.AlreadyExistException;
import com.example.clanhubadv.service.exception.ExcessRegistrationLimitException;
import com.example.clanhubadv.service.exception.NotFoundException;
import com.example.clanhubadv.service.exception.UserAlreadyExistException;
import com.example.clanhubadv.service.validation.ValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterUserService {

    private final UserRepository userRepository;
    private final UserConverter converter;
    private final ValidationService validation;


    private final UserRegistrationLogRepository logRepository;

    private final IpAddressUtil ipAddressUtil;
    private static final int MAX_REGISTRATIONS = 25;
    private static final int TIME_LIMIT_MINUTES = 5;
    private final EmailService emailService;


    public UserResponseDto createNewUser(RegistrationRequest request, HttpServletRequest httpRequest) {

        String ip = ipAddressUtil.getClientIp(httpRequest);
        LocalDateTime timeLimit = LocalDateTime.now().minusMinutes(TIME_LIMIT_MINUTES);

        long recentRegistrations = logRepository.countRecentRegistrations(ip, timeLimit);
        if (recentRegistrations >= MAX_REGISTRATIONS) {
            throw new ExcessRegistrationLimitException("Maximum number of registrations reached, try again later");
        }

        if (validation.userExists(request.getEmail())) {
            throw new UserAlreadyExistException("User with email " + request.getEmail() + " already exists");
        }
        User newUser = converter.createUserFromDto(request);
        User savedUser = userRepository.save(newUser);

        logRepository.save(new UserRegistrationLog(ip, LocalDateTime.now()));
        emailService.sendConfirmationEmail(newUser.getEmail(), newUser.getUsername());

        return converter.createDtoFromUser(savedUser);
    }

    @Transactional
    public UserResponseDto updateUserProfile(String currentEmail, UpdateProfileRequest request) {

        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new NotFoundException("User with email " + currentEmail + " not found"));

        if (request.getEmail() != null && !request.getEmail().equals(currentEmail)) {
            if (validation.userExists(request.getEmail())) {
                throw new AlreadyExistException("Email " + request.getEmail() + " is already in use");
            }
            user.setEmail(request.getEmail());
        }

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getAvatarId() != null) {
            user.setAvatarId(request.getAvatarId());
        }
        if (request.getAge() != null) {
            user.setAge(request.getAge());
        }

        User updatedUser = userRepository.save(user);

        return converter.createDtoFromUser(updatedUser);
    }
}




