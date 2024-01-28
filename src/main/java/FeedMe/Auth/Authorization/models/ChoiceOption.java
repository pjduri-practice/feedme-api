package FeedMe.Auth.Authorization.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "choice_options")
public class ChoiceOption extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "choice_option_user_reference")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "choice_option_column_layout_reference")
    private ColumnLayout columnLayout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "choice_option_choice_column_reference")
    private ChoiceColumn choiceColumn;

    @Size(max = 255)
    private String name;

    // Add your constructors, getters, and setters here
    public ChoiceOption() {}

    public ChoiceOption(String name, ChoiceColumn choiceColumn, User user, ColumnLayout columnLayout) {
        super();
        this.name = name;
        this.choiceColumn = choiceColumn;
        this.user = user;
        this.columnLayout = columnLayout;
    }

//    public ChoiceOption(ChoiceColumn choiceColumn, User user, ColumnLayout columnLayout) {
//        this("", choiceColumn, user, columnLayout);
//    }

    @Override
    public String toString() {
        return name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChoiceColumn getChoiceColumn() {
        return choiceColumn;
    }

    public void setChoiceColumn(ChoiceColumn choiceColumn) {
        this.choiceColumn = choiceColumn;
    }
}