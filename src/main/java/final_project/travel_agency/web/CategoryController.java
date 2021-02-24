package final_project.travel_agency.web;
import final_project.travel_agency.model.binding.CategoryBindingModel;
import final_project.travel_agency.model.entity.Tour;
import final_project.travel_agency.model.service.CategoryServiceModel;
import final_project.travel_agency.repository.TourRepository;
import final_project.travel_agency.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final TourRepository tourRepository;

    public CategoryController(CategoryService categoryService, TourRepository tourRepository) {
        this.categoryService = categoryService;
        this.tourRepository = tourRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryServiceModel>> getAllCategories(){
     List<CategoryServiceModel> allCategories = this.categoryService.getAllCategories();
      return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }


    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createCategory(@Valid @ModelAttribute CategoryBindingModel categoryBindingModel) throws IOException {
        MultipartFile image = categoryBindingModel.getImage();
        String fileName = image.getOriginalFilename();
        Files.copy(image.getInputStream(),
                Paths.get("C:\\Users\\user\\Desktop\\travel_agency\\src\\main\\resources\\static\\image",fileName));
         String asd = System.getProperty("user.dir");
        Tour tour = this.tourRepository.findById("1178e323-9a4f-4906-9af3-3804489dead8").orElse(null);
        tour.getPhotos().add(fileName);
        tour.setImage(fileName);
        //...
        System.out.println();
        this.categoryService.createCategory(categoryBindingModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
