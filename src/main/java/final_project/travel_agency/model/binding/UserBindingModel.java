package final_project.travel_agency.model.binding;



import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class UserBindingModel {
    private String username;
    private String password;
    private String email;
    private String authority;

    public UserBindingModel() {
    }

    @NotBlank(message = "username is required")
    @Length(min = 3, max = 20, message = "Username must be between 3 and 20 letters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Username must contain only letters!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "password is required")
    @Length(min = 3, max = 20, message = "Username must be between 3 and 20 letters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank(message = "email is required")
    @Email(message = "Please, enter valid email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
