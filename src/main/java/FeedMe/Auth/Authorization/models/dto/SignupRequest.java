package FeedMe.Auth.Authorization.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * A basic sign-up request DTO ((data-transfer-object).
 * Contains a username, an email address, and a password.
 */
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20, message = "\nUsername must be between 3 and 20 characters")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email(message = "\nMust use valid email format (e.g. example@test.com)")
    private String email;

    @NotBlank
    @Size(min = 6, max = 40, message = "\nPassword must be between 6 and 40 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}