package FeedMe.Auth.Authorization.data;

import FeedMe.Auth.Authorization.models.ChoiceColumn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoiceColumnRepository extends CrudRepository<ChoiceColumn, Integer> {
    List<ChoiceColumn> findByNameContaining(String title);
}