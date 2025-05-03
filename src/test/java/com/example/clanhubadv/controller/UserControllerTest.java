//package com.example.familyplanner.controller;
//
//import com.example.familyplanner.dto.requests.signInUp.RegistrationRequest;
//import com.example.familyplanner.dto.requests.UpdateProfileRequest;
//import com.example.familyplanner.dto.responses.UserResponseDto;
//import com.example.familyplanner.entity.Role;
//import com.example.familyplanner.service.FindUserService;
//import com.example.familyplanner.service.RegisterUserService;
//import com.example.familyplanner.service.exception.AlreadyExistException;
//import com.example.familyplanner.service.exception.NotFoundException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.security.Principal;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private FindUserService findUserService;
//
//    @Mock
//    private RegisterUserService registerUserService;
//
//    @InjectMocks
//    private UserController userController;
//
//    private ObjectMapper objectMapper;
//    private UserResponseDto userResponseDto1;
//    private UserResponseDto userResponseDto2;
//    private RegistrationRequest validRegistrationRequest;
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//
//               mockMvc = MockMvcBuilders
//                .standaloneSetup(userController)
//                .setControllerAdvice(new ExceptionHandler())
//                .build();
//
//
//        Role role = new Role();
//        role.setId(UUID.randomUUID());
//        role.setRoleName("USER");
//
//        userResponseDto1 = new UserResponseDto();
//        userResponseDto1.setId(UUID.randomUUID());
//        userResponseDto1.setUsername("User1");
//        userResponseDto1.setEmail("user1@example.com");
//        userResponseDto1.setRole(role);
//
//        userResponseDto2 = new UserResponseDto();
//        userResponseDto2.setId(UUID.randomUUID());
//        userResponseDto2.setUsername("User2");
//        userResponseDto2.setEmail("user2@example.com");
//        userResponseDto2.setRole(role);
//
//        validRegistrationRequest = new RegistrationRequest();
//        validRegistrationRequest.setUsername("NewUser");
//        validRegistrationRequest.setEmail("newuser@example.com");
//        validRegistrationRequest.setPassword("Password!123");
//        validRegistrationRequest.setAge(25);
//        validRegistrationRequest.setAvatarId("testAvatarId");
//    }
//
//    @Test
//    void findAll_shouldReturnListOfUsers() throws Exception {
//        List<UserResponseDto> users = Arrays.asList(userResponseDto1, userResponseDto2);
//        when(findUserService.findAll()).thenReturn(users);
//
//        mockMvc.perform(get("/api/users"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].username").value("User1"))
//                .andExpect(jsonPath("$[0].email").value("user1@example.com"))
//                .andExpect(jsonPath("$[1].username").value("User2"))
//                .andExpect(jsonPath("$[1].email").value("user2@example.com"));
//    }
//
//    @Test
//    void findUserByEmail_shouldReturnUser_whenUserExists() throws Exception {
//        when(findUserService.findUserByEmail("user1@example.com")).thenReturn(userResponseDto1);
//
//        mockMvc.perform(get("/api/users/by-email/user1@example.com"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.username").value("User1"))
//                .andExpect(jsonPath("$.email").value("user1@example.com"));
//    }
//
//    @Test
//    void findUserByEmail_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
//        when(findUserService.findUserByEmail(anyString())).thenThrow(new NotFoundException("User not found"));
//
//        mockMvc.perform(get("/api/users/by-email/nonexistent@example.com"))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("User not found"));
//    }
//
//    @Test
//    void createNewUser_shouldReturnCreatedUser_whenDataIsValid() throws Exception {
//        when(registerUserService.createNewUser(any(RegistrationRequest.class), any(HttpServletRequest.class))).thenReturn(userResponseDto1);
//
//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(validRegistrationRequest)))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.username").value("User1"))
//                .andExpect(jsonPath("$.email").value("user1@example.com"));
//    }
//
//    @Test
//    void createNewUser_shouldReturnBadRequest_whenUserAlreadyExists() throws Exception {
//        when(registerUserService.createNewUser(any(RegistrationRequest.class), any(HttpServletRequest.class)))
//                .thenThrow(new AlreadyExistException("User with email newuser@example.com already exists"));
//
//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(validRegistrationRequest)))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("User with email newuser@example.com already exists"));
//    }
//
//    @Test
//    void createNewUser_shouldReturnBadRequest_whenDataIsInvalid() throws Exception {
//        RegistrationRequest invalidRequest = new RegistrationRequest();
//        invalidRequest.setUsername("");
//        invalidRequest.setEmail("invalid-email");
//        invalidRequest.setPassword("short");
//
//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidRequest)))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void updateUserProfile_shouldUpdateUserWhenAuthenticated() throws Exception {
//        // Create a principal for authentication
//        Principal mockPrincipal = mock(Principal.class);
//        when(mockPrincipal.getName()).thenReturn("user1@example.com");
//
//        UpdateProfileRequest updateRequest = new UpdateProfileRequest();
//        updateRequest.setUsername("UpdatedUser");
//        updateRequest.setEmail("updated@example.com");
//        updateRequest.setAvatarId("avatar2");
//        updateRequest.setAge(30);
//
//        when(registerUserService.updateUserProfile(eq("user1@example.com"), any(UpdateProfileRequest.class)))
//                .thenReturn(userResponseDto1);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/profile")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateRequest))
//                        .principal(mockPrincipal))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.username").value("User1"))
//                .andExpect(jsonPath("$.email").value("user1@example.com"));
//
//        verify(registerUserService).updateUserProfile(eq("user1@example.com"), any(UpdateProfileRequest.class));
//    }
//
//    @Test
//    void updateUserProfile_shouldReturnUnauthorizedWhenNotAuthenticated() throws Exception {
//        UpdateProfileRequest updateRequest = new UpdateProfileRequest();
//        updateRequest.setUsername("UpdatedUser");
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/profile")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateRequest)))
//                .andDo(print())
//                .andExpect(status().isUnauthorized());
//
//        verify(registerUserService, never()).updateUserProfile(anyString(), any(UpdateProfileRequest.class));
//    }
//}