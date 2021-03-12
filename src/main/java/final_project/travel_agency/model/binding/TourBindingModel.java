package final_project.travel_agency.model.binding;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TourBindingModel {
    private String name;
    private String description;
    private String region;
    private Integer participants;
    private String difficultyLevel;
    private MultipartFile image;
    private BigDecimal price;
    private String category;
    private LocalDate startDate;

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

    @NotNull(message = "Description is required")
    @Length(min = 3,max = 200, message = "Tour name must contain between 3 and 200 letters")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Region is required")
    @Length(min = 3,max = 200, message = "Tour name must contain between 2 and 200 letters")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NotNull
    @Min(value = 0, message = "Number participants must be greater than 0")
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
    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
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

    @DateTimeFormat( iso = DateTimeFormat.ISO.DATE)
    @Future(message = "Date must be in a future")
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
