package FeedMe.Auth.Authorization.data;

import FeedMe.Auth.Authorization.models.ChoiceColumn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceColumnRepository extends CrudRepository<ChoiceColumn, Integer> {
}