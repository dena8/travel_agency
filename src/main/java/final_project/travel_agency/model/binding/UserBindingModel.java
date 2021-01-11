package final_project.travel_agency.model.binding;



import java.util.List;

public class UserBindingModel {
    private String username;
    private String password;
    private String email;
    private List<AuthorityBindingModel> authorities;

    public UserBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AuthorityBindingModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<AuthorityBindingModel> authorities) {
        this.authorities = authorities;
    }
}
