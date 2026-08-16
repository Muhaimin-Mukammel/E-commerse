package authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record SigninRequest(
        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password
) {
}
