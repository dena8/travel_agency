package final_project.travel_agency.model.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table
public class Category extends BaseEntity {
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    @Length(min = 3, max = 20, message = "Category name must contain between 3 and 20 letters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
