package FeedMe.Auth.Authorization.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "column_layouts")
public class ColumnLayout extends AbstractEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference // Set this reference as a back reference for the JSON marshalling
    private User user;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference // Set this reference as a managed reference for the JSON marshalling
    private List<ChoiceColumn> choiceColumns = new ArrayList<>();

    public ColumnLayout() {}

    public ColumnLayout(String name, User user) {
        super();
        this.name = name;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public List<ChoiceColumn> getChoiceColumns() {
        return choiceColumns;
    }

    public void setName(String name) {
        this.name = name;
    }
}
