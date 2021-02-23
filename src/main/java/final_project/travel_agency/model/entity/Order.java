package final_project.travel_agency.model.entity;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private Instant buyDate;
    private User customer;
    private List<Tour> buyingProducts= new ArrayList<>();
    private Boolean isConfirm =false;


    public Order() {
       this.buyDate = Instant.now();
    }

    @Column
    public Instant getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Instant buyDate) {
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
