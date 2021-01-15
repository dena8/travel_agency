package final_project.travel_agency.model.binding;

import final_project.travel_agency.model.entity.Category;
import final_project.travel_agency.model.entity.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class TourBindingModel {
    private String name;
    private String description;
    private String  startAndEnd;
    private Integer participants;
    private String difficultyLevel;
    private String image;
    private BigDecimal price;
    private String category;

    public TourBindingModel() {
    }

    @NotNull
    @Length(min = 3,max = 20, message = "Tour name must contain between 3 and 20 letters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Length(min = 3,max = 200, message = "Tour name must contain between 3 and 200 letters")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Pattern(regexp = "^([0-9]{2}\\/){2}[0-9]{2}-([0-9]{2}\\/){2}[0-9]{2}$")
    public String getStartAndEnd() {
        return startAndEnd;
    }

    public void setStartAndEnd(String startAndEnd) {
        this.startAndEnd = startAndEnd;
    }

    @NotNull
    @Min(0)
    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    @NotNull
    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    @NotNull
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @DecimalMin(value = "0",message = "Price must be positive number")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
