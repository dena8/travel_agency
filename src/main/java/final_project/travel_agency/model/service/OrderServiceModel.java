package final_project.travel_agency.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceModel extends BaseServiceModel {
    private String id;
    private LocalDateTime buyDate = LocalDateTime.now();
    private UserServiceModel customer;
    private List<TourServiceModel> buyingProducts= new ArrayList<>();

    public OrderServiceModel() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDateTime buyDate) {
        this.buyDate = buyDate;
    }

    public UserServiceModel getCustomer() {
        return customer;
    }

    public void setCustomer(UserServiceModel customer) {
        this.customer = customer;
    }

    public List<TourServiceModel> getBuyingProducts() {
        return buyingProducts;
    }

    public void setBuyingProducts(List<TourServiceModel> buyingProducts) {
        this.buyingProducts = buyingProducts;
    }
}
