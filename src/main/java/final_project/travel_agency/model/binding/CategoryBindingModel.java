package final_project.travel_agency.model.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class CategoryBindingModel {
    private String name;
    private MultipartFile image;


    public CategoryBindingModel() {
    }

    @NotBlank
    @Length(min = 3, max = 20, message = "Category name must contain between 3 and 20 letters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
