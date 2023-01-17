package FeedMe.Auth.Authorization.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User extends AbstractEntity {

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotNull
    private String pwHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ensure our choice columns are lazily loaded (only load/read them when specifically requested)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference(value = "choice_column_user_reference") // Set this reference as a managed reference for the JSON marshalling
    private List<ChoiceColumn> choiceColumns = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference(value = "column_layout_user_reference") // Set this reference as a managed reference for the JSON marshalling
    private List<ColumnLayout> columnLayouts = new ArrayList<>();

    public User() {}

    public User(String username, String email, String pwHash) {
        this.username = username;
        this.email = email;
        this.pwHash = pwHash;
    }

    public String getUsername() {
        return username;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public List<ChoiceColumn> getChoiceColumns() {
        return choiceColumns;
    }

    public List<ColumnLayout> getColumnLayouts() {
        return columnLayouts;
    }

    public void setChoiceColumns(List<ChoiceColumn> choiceColumns) {
        this.choiceColumns = choiceColumns;
    }

    public void setColumnLayouts(List<ColumnLayout> columnLayouts) {
        this.columnLayouts = columnLayouts;
    }
}
