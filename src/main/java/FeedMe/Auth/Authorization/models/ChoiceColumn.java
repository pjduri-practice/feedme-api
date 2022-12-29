package FeedMe.Auth.Authorization.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
public class ChoiceColumn extends AbstractEntity {

//    @Id
//    @GeneratedValue
//    private int id;

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

//    public int getId() {
//        return id;
//    }

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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ChoiceColumn that = (ChoiceColumn) o;
//        return id == that.id;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }

}
