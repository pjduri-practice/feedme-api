package FeedMe.Auth.Authorization.data;


import FeedMe.Auth.Authorization.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}
