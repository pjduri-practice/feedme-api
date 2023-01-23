package FeedMe.Auth.Authorization.controllers;

import FeedMe.Auth.Authorization.data.UserRepository;
import FeedMe.Auth.Authorization.models.User;
import FeedMe.Auth.Authorization.models.dto.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Handles routes for user information.<br>
 * <b>/api/users</b>
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Handles a request to fetch the information of a signed-in user.
     * @param authentication Information about the authentication status of the
     *                       client performing this request.
     * @return UserInfoResponse containing the signed-in user's username, email
     * address, and password if the requesting client is signed in. Returns null
     * if the client making the request does not have a valid authentication session.
     */
    @GetMapping
    public UserInfoResponse getUser(Authentication authentication) {
        // Retrieves the username from the authentication token provided in
        // the authentication cookie header sent by the client making this
        // request.
        String username = authentication.getName();

        // Ensures that a username was found within the token.
        if (username != null) {
            // Attempts to find a user via the username recovered from the
            // auth token.
            Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
            if (user.isPresent()) {
                // Fetches the user entity if a user was found within the
                // database and returns a UserInfoResponse object with that
                // user's username, email address, and unique ID.
                User userEntity = user.get();
                UserInfoResponse response = new UserInfoResponse(
                        userEntity.getId(),
                        userEntity.getUsername(),
                        userEntity.getEmail()
                );
                return response;
            }
        }
        return null;
    }

}