package FeedMe.Auth.Authorization.data;

import FeedMe.Auth.Authorization.models.ChoiceColumn;
import FeedMe.Auth.Authorization.models.User;
import FeedMe.Auth.Authorization.models.UserIngredients;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserIngredientsRepository extends CrudRepository<UserIngredients, Integer> {

    List<UserIngredients> findByNameContaining(String name);
    List<UserIngredients> findByUser(User user);
    Optional<UserIngredients> findByUserAndId(User user, int id);

}
