package final_project.travel_agency.model.binding;

import org.springframework.web.multipart.MultipartFile;


import javax.validation.constraints.NotBlank;
import java.util.List;

public class GalleryBindingModel {
    private List<MultipartFile> images;
    private String description;


    public GalleryBindingModel() {
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    @NotBlank
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
