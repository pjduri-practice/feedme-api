package FeedMe.Auth.Authorization.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "choice_columns")
public class ChoiceColumn extends AbstractEntity {

    // ensure our user is lazily loaded (only load/read it when specifically requested)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "choice_column_user_reference") // Set this reference as a back reference for the JSON marshalling
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "choice_column_layout_reference") // Set this reference as a back reference for the JSON marshalling
    private ColumnLayout columnLayout;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "choice_column_id", referencedColumnName = "id")
    @JsonManagedReference(value = "choice_column_reference") // Set this reference as a managed reference for the JSON marshalling
    private List<ChoiceOption> choiceOptions;

    public ChoiceColumn() {}

    public ChoiceColumn(String name, ColumnLayout columnLayout, User user, List<ChoiceOption> choiceOptions) {
        super();
        this.name = name;
        this.columnLayout = columnLayout;
        this.user = user;
        this.choiceOptions = choiceOptions;
    }

    public ChoiceColumn(String name, ColumnLayout columnLayout, User user) {
        this(name, columnLayout, user, new ArrayList<>());
        this.name = name;
        this.columnLayout = columnLayout;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChoiceOption> getChoiceOptions() {
        return choiceOptions;
    }

    public void setChoiceOptions(List<ChoiceOption> choiceOptions) {
        this.choiceOptions = choiceOptions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ColumnLayout getColumnLayout() {
        return columnLayout;
    }

    public void setColumnLayout(ColumnLayout columnLayout) {
        this.columnLayout = columnLayout;
    }

    @Override
    public String toString() {
        return name;
    }

}
