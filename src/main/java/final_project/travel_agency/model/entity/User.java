package final_project.travel_agency.model.entity;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    private String username;
    private String password;
    private String email;
    private List<Authority> authorities;
    private List<Tour> createdTours = new ArrayList<>();
    private List<Tour> cart = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    public User() {

    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

    @Column(nullable = false)
    @Length(min = 3,max = 20, message = "Username must contain between 3 and 20 letters")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false, unique = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    @Email(message = "Email is not valid")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @OneToMany
    public List<Tour> getCreatedTours() {
        return createdTours;
    }

    public void setCreatedTours(List<Tour> createdTours) {
        this.createdTours = createdTours;
    }

    @ManyToMany()
    public List<Tour> getCart() {
        return cart;
    }

    public void setCart(List<Tour> cart) {
        this.cart = cart;
    }

    @OneToMany(mappedBy = "customer")
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
