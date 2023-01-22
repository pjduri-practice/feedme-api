package FeedMe.Auth.Authorization.data;

import FeedMe.Auth.Authorization.models.ChoiceColumn;
import FeedMe.Auth.Authorization.models.UserIngredients;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserIngredientsRepository extends CrudRepository<UserIngredients, Integer> {

    List<ChoiceColumn> findByNameContaining(String name);

}
