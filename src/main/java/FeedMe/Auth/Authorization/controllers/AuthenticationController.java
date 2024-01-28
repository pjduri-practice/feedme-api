package FeedMe.Auth.Authorization.controllers;

import FeedMe.Auth.Authorization.data.ChoiceColumnRepository;
import FeedMe.Auth.Authorization.data.ChoiceOptionRepository;
import FeedMe.Auth.Authorization.data.ColumnLayoutRepository;
import FeedMe.Auth.Authorization.data.UserRepository;
import FeedMe.Auth.Authorization.models.ChoiceColumn;
import FeedMe.Auth.Authorization.models.ChoiceOption;
import FeedMe.Auth.Authorization.models.ColumnLayout;
import FeedMe.Auth.Authorization.models.User;
import FeedMe.Auth.Authorization.models.dto.LoginRequest;
import FeedMe.Auth.Authorization.models.dto.MessageResponse;
import FeedMe.Auth.Authorization.models.dto.SignupRequest;
import FeedMe.Auth.Authorization.models.dto.UserInfoResponse;
import FeedMe.Auth.Authorization.security.JwtUtils;
import FeedMe.Auth.Authorization.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


/**
 * Handles routes for user sign-up, sign-in, and sign-out.<br>
 * <b>/api/auth</b>
 */
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ColumnLayoutRepository columnLayoutRepository;

    @Autowired
    ChoiceColumnRepository choiceColumnRepository;

    @Autowired
    ChoiceOptionRepository choiceOptionRepository;

    /**
     * Handles a user sign-in request, accepting a username and password to generate a
     * JWT (JSON Web Token) if the login request is valid. If valid, the generated JWT
     * is returned to the user in the response as a cookie that will be saved in their
     * browser. Each subsequent request will/should contain this cookie, allowing for
     * the backend to fetch information about the user that is signed in.
     *
     * @param loginRequest The sign-in request, containing the username and password
     *                     provided by the user.
     * @return A ResponseEntity containing the details of the user signing in and a
     * cookie with their JWT authentication token.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Attempts to authenticate the username and password provided in the request body.
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // Sets the application security context to the current authentication attempt.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Attempts to find the details of the user that is being signed in based off
        // of the authentication attempt.
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Generates a new JSON Web Token from the user's information, to be used for
        // authenticating the user in future requests to the backend.
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        // Responds to the client with the generated JWT cookie and the details of the
        // user that has been signed in.
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail()));
    }

    /**
     * Handles a user sign-up request, accepting a username, email address, and
     * password to create a new user entity.
     *
     * @param signUpRequest The user's desired username, email address, and password.
     * @return If successful, a ResponseEntity containing a MessageResponse informing
     * the client that the user was successfully created. Responds with a 400 Bad
     * Request error if a malformed email address or an already existing username
     * were provided.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, Errors errors) {
        // Create a StringBuilder to collect error messages
        StringBuilder errorMessages = new StringBuilder();

        // Validates that the username being registered does not already exist.
        if (userRepository.existsByUsernameIgnoreCase(signUpRequest.getUsername())) {
            errorMessages.append("\nUsername is already taken");
        }

        // Validates that the email being registered does not already exist.
        if (userRepository.existsByEmailIgnoreCase(signUpRequest.getEmail())) {
            errorMessages.append("\nEmail is already in use");
        }

        if (errors.hasErrors()) {
            // Loop through errors and append error messages
            errors.getAllErrors()
                    .forEach(error -> errorMessages
                            .append("\n")
                            .append(error.getDefaultMessage()));
        }
        if (!errorMessages.isEmpty()) {
            // Return the error messages as a response
//            return ResponseEntity.badRequest().body(new MessageResponse("BAD!"));
            return ResponseEntity.badRequest().body(new MessageResponse(errorMessages.toString()));
        }

        // Create the user's account...
        try {
            User user = new User(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));

            // ...and save it to the database.
            user = userRepository.save(user);

            // creating a default column layout on register
            ColumnLayout columnLayout = new ColumnLayout("Default", user);
            columnLayout = columnLayoutRepository.save(columnLayout);

            // create the default columns
            ChoiceColumn choiceColumnSnacks = new ChoiceColumn("Snacks", columnLayout, user);
            ChoiceColumn choiceColumnTakeOut = new ChoiceColumn("Take Out", columnLayout, user);
            choiceColumnRepository.save(choiceColumnSnacks);
            choiceColumnRepository.save(choiceColumnTakeOut);

            // create a default ChoiceOption for each column
            ChoiceOption choiceOptionSnack = new ChoiceOption("", choiceColumnSnacks, user, columnLayout);
            ChoiceOption choiceOptionTakeout = new ChoiceOption("", choiceColumnTakeOut, user, columnLayout);
            choiceOptionRepository.save(choiceOptionSnack);
            choiceOptionRepository.save(choiceOptionTakeout);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(errorMessages.toString()));
        }

        // Responds to the client with a 200 OK status and a message notifying of
        // successful user registration.
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     * Handles a user sign-out request, deleting their active JWT session cookie.
     *
     * @return A ResponseEntity containing a MessageResponse notifying the client
     * that the user has been signed out.
     */
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        // Creates an empty cookie to replace the client's existing JWT cookie.
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();

        // Responds to the client with a 200 OK status, an empty authentication cookie
        // (to clear it out), and a message notifying of successful user sign-out.
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}