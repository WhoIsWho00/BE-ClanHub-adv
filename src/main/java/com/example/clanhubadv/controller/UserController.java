package com.example.clanhubadv.controller;


import com.example.clanhubadv.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

//    private final FindUserService findService;
//    private final RegisterUserService registerService;
    private final UserService userService;
//    private final TaskService taskService;

    @Operation(summary = "Delete user", description = "Delete user by its specific id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                               {
                                                  "message": "User successfully deleted"
                                                }
                                               """
                            ))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized (authentication required)",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                               {
                                                  "timestamp": "2025-03-25T16:26:19.597Z",
                                                  "status": 401,
                                                 "error": "Unauthorized",
                                                 "message": "Authentication required. Please log in.",
                                                 "path": "/api/users"
                                               }
                                               """
                            ))),
                    @ApiResponse(responseCode = "403", description = "Forbidden (insufficient permissions)",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                               {
                                                 "timestamp": "2025-03-25T16:26:19.597Z",
                                                "status": 403,
                                                 "error": "Forbidden",
                                                 "message": "You do not have permission to access that user.",
                                                 "path": "/api/users"
                                               }
                                               """
                            ))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                              {
                                                  "timestamp": "2025-03-25T16:26:19.597Z",
                                                 "status": 404,
                                                 "error": "Not Found",
                                                  "message": "",
                                                  "path": "/api/users"
                                                }
                                              """
                            ))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = """
                                               {
                                                 "timestamp": "2025-03-25T16:26:19.597Z",
                                                  "status": 500,
                                                 "error": "Internal Server Error",
                                                 "message": "An unexpected error occurred.",
                                                 "path": "/api/users"
                                               }
                                               """
                            )))
            }
    )

    @DeleteMapping
    public ResponseEntity<String> deleteUserById(@RequestParam UUID  id){
        userService.deleteUserById(id);
        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }

//    @Operation(
//            summary = "Create user",
//                description = "Create user. Store him in DataBase, and return in response only non confidential info",
//                    responses = {   @ApiResponse(responseCode = "201", description = "User successfully created"),
//                                    @ApiResponse(responseCode = "400", description = "User already exist"),
//                                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
//                                    @ApiResponse(responseCode = "500", description = "Iternal Server Error")})
//    @PostMapping
//    public ResponseEntity<UserResponseDto> createNewUser(@Valid @RequestBody RegistrationRequest request, HttpServletRequest httpRequest) {
//        return new ResponseEntity<>(registerService.createNewUser(request, httpRequest), HttpStatus.CREATED);
//    }
//
//    @Operation(
//            summary = "Find all users",
//                description = "Find all users that already stores in DataBase and return in response only non Confidential information",
//                    responses = {   @ApiResponse(responseCode = "200", description = "All users found"),
//                                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
//                                    @ApiResponse(responseCode = "500", description = "Iternal Server Error")})
//    @GetMapping
//    public List<UserResponseDto> findAll() {
//        return findService.findAll();
//    }
//
//
//    @Operation(
//            summary = "Find user by email",
//                description = "Find user that already exist in DataBase via his unique and personal email",
//                    responses = {   @ApiResponse(responseCode = "200", description = "All users found"),
//                                    @ApiResponse(responseCode = "400", description = "Validation Error"),
//                                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
//                                    @ApiResponse(responseCode = "404", description = "User not found"),
//                                    @ApiResponse(responseCode = "500", description = "Iternal Server Error")})
//    @GetMapping("/by-email/{email}")
//    public UserResponseDto findUserByEmail(@PathVariable String email) {
//        return findService.findUserByEmail(email);
//    }
//
//    @Operation(
//            summary = "Update user profile",
//            description = "Updates authenticated user's profile information",
//            security = @SecurityRequirement(name = "JWT"),
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Profile successfully updated"),
//                    @ApiResponse(responseCode = "400", description = "Validation error or email already in use"),
//                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
//                    @ApiResponse(responseCode = "404", description = "User not found"),
//                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
//
//                    @ApiResponse(responseCode = "201", description = "Profile successfully updated",
//                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
//                                    value = """
//                                            {
//                                              "user": {
//                                                "id": 2,
//                                                "name": "new_name",
//                                              },
//                                              "message": "Profile successfully updated",
//                                              "status": "success"
//                                            }
//                                            """
//                            ))),
//                    @ApiResponse(responseCode = "401", description = "Task validation failed",
//                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
//                                    value = """
//                                            {
//                                              "timestamp": "2025-03-25T16:26:19.597Z",
//                                              "status": 401,
//                                              "error": "Bad Request",
//                                              "message": {
//                                              "title": "title is required"
//                                              },
//                                              "path": "/api/users/profile"
//                                            }
//                                            """
//                            ))),
//                    @ApiResponse(responseCode = "404", description = "Not Found",
//                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
//                                    value = """
//                                            {
//                                              "timestamp": "2025-03-25T16:26:19.597Z",
//                                              "status": 404,
//                                              "error": "Not Found",
//                                              "message": "User not found",
//                                              "path": "/api/users/profile"
//                                            }
//                                            """
//                            ))),
//                    @ApiResponse(responseCode = "500", description = "Internal server error",
//                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
//                                    value = """
//                                            {
//                                              "timestamp": "2025-03-25T16:26:19.597Z",
//                                              "status": 500,
//                                              "error": "Internal Server Error",
//                                              "message": "An unexpected error occurred.",
//                                              "path": "/api/users/profile"
//                                            }
//                                            """
//                            )))
//            }
//    )
//    @PutMapping("/profile")
//    public ResponseEntity<UserResponseDto> updateUserProfile(
//            Principal principal,
//            @Valid @RequestBody UpdateProfileRequest request) {
//
//        if (principal == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        String email = principal.getName();
//        UserResponseDto updatedUser = registerService.updateUserProfile(email, request);
//
//        return ResponseEntity.ok(updatedUser);
//    }
}

