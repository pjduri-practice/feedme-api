package FeedMe.Auth.Authorization.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A request filter run against all protected routes that searches
 * for and validates JWT authentication cookies, attaching any valid
 * authentication context found onto the request for use in downstream
 * services or controllers (such as <b>UserController</b>).
 */
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /**
     * Searches for a JWT authentication cookie within the given
     * request object.
     * @param request The client request object, containing information on the
     *                client and request being sent to the backend.
     * @return The JWT authentication token string, if one was found.
     */
    private String parseJwt(HttpServletRequest request) {
        return jwtUtils.getJwtFromCookies(request);
    }

    /**
     * Filters a single request being sent in to a protected backend route,
     * adding an authentication context to the request if a valid JWT token
     * was provided by the client making the request.
//     * @throws ServletException
//     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Searches for a JWT authentication token within the client's request.
            String jwt = parseJwt(request);

            // Ensures that a token was found and is valid.
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // Attempts to fetch a username from the JWT authentication token.
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // Fetches the details of a user by the username found within the
                // JWT authentication token.
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validates the user details and adds the user to the request context
                // for use by downstream services and controllers.
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        // Executes the JWT request filter.
        filterChain.doFilter(request, response);
    }
}
