package com.B2B.SP.user.dto;

import com.B2B.SP.user.model.User;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 255, message = "Username cannot exceed 255 characters")
    private String userName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String userEmail;

    @NotNull(message = "Account type cannot be null")
    @Enumerated
    private User.AccountType accountType;

    @NotNull(message = "Account status cannot be null")
    @Enumerated
    private User.AccountStatus accountStatus;
}
