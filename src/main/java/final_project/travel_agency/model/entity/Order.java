package final_project.travel_agency.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private LocalDateTime buyDate = LocalDateTime.now();
    private User customer;
    private List<Tour> buyingProducts= new ArrayList<>();
    private Boolean isConfirm =false;


    public Order() {
    }

    @Column(columnDefinition = "boolean default false")
    public LocalDateTime getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDateTime buyDate) {
        this.buyDate = buyDate;
    }

    @ManyToOne
    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    @ManyToMany
    public List<Tour> getBuyingProducts() {
        return buyingProducts;
    }

    public void setBuyingProducts(List<Tour> buyingProducts) {
        this.buyingProducts = buyingProducts;
    }

    @Column
    public Boolean getConfirm() {
        return isConfirm;
    }

    public void setConfirm(Boolean confirm) {
        isConfirm = confirm;
    }
}
