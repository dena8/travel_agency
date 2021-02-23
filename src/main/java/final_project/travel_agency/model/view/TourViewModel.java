package final_project.travel_agency.model.view;



import final_project.travel_agency.model.service.BaseServiceModel;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TourViewModel extends BaseServiceModel {
    private String name;
    private String description;
    private Integer participants;
    private String difficultyLevel;
    private String image;
    private BigDecimal price;
    private Boolean enabled;
    private CategoryViewModel category;
    private UserViewModel creator;
    private LocalDate startDate;

    public TourViewModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public CategoryViewModel getCategory() {
        return category;
    }

    public void setCategory(CategoryViewModel category) {
        this.category = category;
    }

    public UserViewModel getCreator() {
        return creator;
    }

    public void setCreator(UserViewModel creator) {
        this.creator = creator;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
