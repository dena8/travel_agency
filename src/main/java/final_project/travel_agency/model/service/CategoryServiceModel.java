package final_project.travel_agency.model.service;

import org.hibernate.validator.constraints.Length;

public class CategoryServiceModel extends BaseServiceModel {
    private String name;

    public CategoryServiceModel() {
    }

   // @Length(min = 3, max = 20, message = "Category name must contain between 3 and 20 letters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
