package FeedMe.Auth.Authorization.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    private List<String> items = new ArrayList<>();

    public ChoiceColumn() {}

    public ChoiceColumn(String name, List<String> items, User user, ColumnLayout columnLayout) {
        super();
        this.name = name;
        this.items = items;
        this.user = user;
        this.columnLayout = columnLayout;
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
