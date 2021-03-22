package final_project.travel_agency.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Table(name = "logs")
@Entity
public class Log extends BaseEntity {
    private String customer;
    private String error;
    private String stacktrace;
    private Instant date;

    public Log() {
    }

    @Column(nullable = false)
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Column(nullable = false)
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Column(columnDefinition = "text")
    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    @Column(nullable = false)
    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}
