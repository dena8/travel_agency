package final_project.travel_agency.model.view;

import final_project.travel_agency.model.entity.Tour;
import final_project.travel_agency.model.entity.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdersViewModel {
    private String id;
    private Instant buyDate;
    private UserViewModel customer;
    private List<TourViewModel> buyingProducts= new ArrayList<>();

    public OrdersViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Instant buyDate) {
        this.buyDate = buyDate;
    }

    public UserViewModel getCustomer() {
        return customer;
    }

    public void setCustomer(UserViewModel customer) {
        this.customer = customer;
    }

    public List<TourViewModel> getBuyingProducts() {
        return buyingProducts;
    }

    public void setBuyingProducts(List<TourViewModel> buyingProducts) {
        this.buyingProducts = buyingProducts;
    }
}
