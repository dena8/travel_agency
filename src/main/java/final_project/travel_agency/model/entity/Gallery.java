package final_project.travel_agency.model.entity;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "galleries")
public class Gallery extends BaseEntity {
    private List<String> images;
    private String description;

    public Gallery() {
    }

    @ElementCollection()
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Column(columnDefinition = "text",nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
