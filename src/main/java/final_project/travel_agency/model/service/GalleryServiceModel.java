package final_project.travel_agency.model.service;

import java.util.List;

public class GalleryServiceModel extends BaseServiceModel {
    private List<String> images;
    private String description;

    public GalleryServiceModel() {
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
