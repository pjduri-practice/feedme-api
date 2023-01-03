package FeedMe.Auth.Authorization.models.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * A basic login request DTO (data-transfer-object).
 * Contains a username and a password.
 */
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
