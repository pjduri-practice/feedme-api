package FeedMe.Auth.Authorization.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class UserIngredients extends AbstractEntity {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "choice_column_user_reference")
    private User user;

    public UserIngredients() {}

    public UserIngredients(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
