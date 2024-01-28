package FeedMe.Auth.Authorization.data;

import FeedMe.Auth.Authorization.models.ChoiceColumn;
import FeedMe.Auth.Authorization.models.ChoiceOption;
import FeedMe.Auth.Authorization.models.ColumnLayout;
import FeedMe.Auth.Authorization.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChoiceOptionRepository extends CrudRepository<ChoiceOption, Integer> {
    List<ChoiceOption> findByUser(User user);
    List<ChoiceOption> findByChoiceColumn(ChoiceColumn choiceColumn);
    Optional<ChoiceOption> findByUserAndId(User user, Integer id);
    Optional<ChoiceOption> findByChoiceColumnAndId(ChoiceColumn choiceColumn, Integer id);
    List<ChoiceOption> findByColumnLayout(ColumnLayout columnLayout);
    Optional<ChoiceOption> findByColumnLayoutAndId(ColumnLayout columnLayout, Integer id);
}
