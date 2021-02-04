package final_project.travel_agency.model.view;

import java.util.ArrayList;
import java.util.List;

public class CurrentUserViewModel {
    private String id;
    private String username;
    private String email;
    private List<TourViewModel> createdTours = new ArrayList<>();
    private List<TourViewModel> cart = new ArrayList<>();

    public CurrentUserViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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

    public List<TourViewModel> getCreatedTours() {
        return createdTours;
    }

    public void setCreatedTours(List<TourViewModel> createdTours) {
        this.createdTours = createdTours;
    }

    public List<TourViewModel> getCart() {
        return cart;
    }

    public void setCart(List<TourViewModel> cart) {
        this.cart = cart;
    }
}
