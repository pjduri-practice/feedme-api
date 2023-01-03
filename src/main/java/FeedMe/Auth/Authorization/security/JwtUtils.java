package FeedMe.Auth.Authorization.security;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.*;

import java.util.Date;

/**
 * A utility class to handle common JWT functionality.
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * The JWT secret key, provided in <b>/src/main/resources/application.properties</b>
     */
    @Value("${backend.app.jwtSecret}")
    private String jwtSecret;

    /**
     * The JWT token expiration time (in milliseconds), provided in
     * <b>/src/main/resources/application.properties</b>
     */
    @Value("${backend.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * The name of the JWT authentication cookie, provided in
     * <b>/src/main/resources/application.properties</b>
     */
    @Value("${backend.app.jwtCookieName}")
    private String jwtCookie;

    /**
     * Retrieves the JWT authentication cookie from a client request object.
     * @param request The client request object, containing information on the
     *                client and request being sent to the backend.
     * @return The JWT token string, or null if none was found.
     */
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * Generates a JWT authentication cookie from a user's details,
     * namely their username.
     * @param userPrincipal The user details object of the user to
     *                      create an authentication cookie for.
     * @return A ResponseCookie object containing the JWT authentication
     * cookie and token.
     */
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        return ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
    }

    /**
     * Creates a null JWT authentication cookie, generally used to clear
     * out a client's JWT authentication cookie and sign them out.
     * @return
     */
    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookie, null).path("/api").build();
    }

    /**
     * Retrieves the username stored in a JWT authentication token.
     * @param token The token to retrieve the username from.
     * @return The username assigned to the given authentication token.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Ensures that the given JWT authentication token is valid and
     * has not expired.
     * @param authToken The authentication token to validate.
     * @return True if the token is valid and not expired, false otherwise.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    /**
     * Generates a JWT authentication token with a given username.
     * @param username The username to generate the authentication token with.
     * @return The newly generated and signed authentication token, containing
     * the given username as the JWT subject.
     */
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                // Add the username as the JWT subject.
                .setSubject(username)
                // Set the token's issue date/time.
                .setIssuedAt(new Date())
                // Set the token's expiration date/time.
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                // Sign the token with the configured JWT secret.
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                // Convert the JWT into a string that can be put into a cookie.
                .compact();
    }
}