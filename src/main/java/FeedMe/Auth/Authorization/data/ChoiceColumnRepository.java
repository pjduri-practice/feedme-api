package FeedMe.Auth.Authorization.data;

import FeedMe.Auth.Authorization.models.ChoiceColumn;
import FeedMe.Auth.Authorization.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChoiceColumnRepository extends CrudRepository<ChoiceColumn, Integer> {
    List<ChoiceColumn> findByNameContaining(String title);
    List<ChoiceColumn> findByUser(User user);
    Optional<ChoiceColumn> findByUserAndId(User user, int id);
}