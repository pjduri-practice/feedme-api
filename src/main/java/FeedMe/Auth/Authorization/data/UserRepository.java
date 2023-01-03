package FeedMe.Auth.Authorization.data;


import FeedMe.Auth.Authorization.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {


    Optional<User> findByUsername(String username);

    /**
     * Validates if a username already exists in the database.
     * @param username The username to search for.
     * @return True if the username already exists, false if not.
     */
    Boolean existsByUsername(String username);

    /**
     * Validates if an email address already exists in the database.
     * @param email The email address to search for.
     * @return True if the email address already exists, false if not.
     */
    Boolean existsByEmail(String email);

}
