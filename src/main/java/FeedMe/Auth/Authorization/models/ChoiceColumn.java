package FeedMe.Auth.Authorization.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "choice_columns")
public class ChoiceColumn extends AbstractEntity {

    @ManyToOne
    private User user;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    private List<String> items = new ArrayList<>();

    public ChoiceColumn() {}

    public ChoiceColumn(String name, List<String> items, User user) {
        super();
        this.name = name;
        this.items = items;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return name;
    }

}
