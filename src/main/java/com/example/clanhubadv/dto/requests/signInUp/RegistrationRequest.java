package com.example.clanhubadv.dto.requests.signInUp;

import com.example.clanhubadv.entity.enums.FamilyRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request model for user registration")
public class RegistrationRequest {
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = "User's username", example = "john_doe", required = true)
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Schema(description = "User's email address", example = "john@example.com", required = true)
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$",
             message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character")
    @Schema(description = "User's password", example = "Password123!", required = true)
    private String password;

    @Pattern(regexp = "^(ROLE_USER|ROLE_ADMIN|ROLE_PARENT|ROLE_CHILD|ROLE_GUEST|ROLE_MODERATOR)$",
             message = "Role must be one of the valid system roles")
    @Schema(description = "User's system role", example = "ROLE_PARENT", required = true)
    private String role;

    @NotNull(message = "Family role cannot be empty")
    @Schema(description = "User's family role", example = "FATHER", required = true)
    private FamilyRole familyRole;

    @Schema(description = "User's family name", example = "Doe Family")
    private String familyName;
    
    @Schema(description = "User's age", example = "25")
    private Integer age;
    
    @Schema(description = "User's avatar ID", example = "avatar1")
    private String avatarId;
    
    @Schema(description = "Invite code for registration", example = "ABC123")
    private String inviteCode;
}

