package FeedMe.Auth.Authorization.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "choice_columns")
public class ChoiceColumn extends AbstractEntity {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    private String description;

    public ChoiceColumn() {}

    public ChoiceColumn(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

}
