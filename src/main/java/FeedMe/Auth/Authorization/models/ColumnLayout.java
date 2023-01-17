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
public class ColumnLayout extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "column_layout_user_reference") // Set this reference as a back reference for the JSON marshalling
    private User user;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "column_layout_id", referencedColumnName = "id")
    @JsonManagedReference(value = "choice_column_layout_reference") // Set this reference as a managed reference for the JSON marshalling
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

    public void setUser(User user) {
        this.user = user;
    }

    public void setChoiceColumns(List<ChoiceColumn> choiceColumns) {
        this.choiceColumns = choiceColumns;
    }
}
