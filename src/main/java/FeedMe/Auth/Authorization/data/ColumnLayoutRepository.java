package FeedMe.Auth.Authorization.data;

import FeedMe.Auth.Authorization.models.ColumnLayout;
import FeedMe.Auth.Authorization.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColumnLayoutRepository extends CrudRepository<ColumnLayout, Integer> {
    List<ColumnLayout> findByNameContaining(String title);
    List<ColumnLayout> findByUser(User user);
    Optional<ColumnLayout> findByUserAndId(User user, int id);
}
