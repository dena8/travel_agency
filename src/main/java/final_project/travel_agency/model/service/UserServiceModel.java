package final_project.travel_agency.model.service;

import final_project.travel_agency.model.entity.Authority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserServiceModel extends BaseServiceModel {
    private String username;
    private String password;
    private String email;
    private List<AuthorityServiceModel> authorities;
    private List<TourServiceModel> createdTours;
    private List<TourServiceModel> cart = new ArrayList<>();


    public UserServiceModel() {
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

    public List<AuthorityServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<AuthorityServiceModel> authorities) {
        this.authorities = authorities;
    }

    public List<TourServiceModel> getCreatedTours() {
        return createdTours;
    }

    public void setCreatedTours(List<TourServiceModel> createdTours) {
        this.createdTours = createdTours;
    }

    public List<TourServiceModel> getCart() {
        return cart;
    }

    public void setCart(List<TourServiceModel> cart) {
        this.cart = cart;
    }
}
