package final_project.travel_agency.model.service;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceModel extends BaseServiceModel {

    private UserServiceModel customer;
    private Instant buyDate;
    private List<TourServiceModel> buyingProducts= new ArrayList<>();

    public OrderServiceModel() {
    }



    public UserServiceModel getCustomer() {
        return customer;
    }

    public Instant getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Instant buyDate) {
        this.buyDate = buyDate;
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
