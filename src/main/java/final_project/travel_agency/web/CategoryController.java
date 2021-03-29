package final_project.travel_agency.web;
import final_project.travel_agency.exception.NotFoundEx;
import final_project.travel_agency.model.binding.CategoryBindingModel;

import final_project.travel_agency.model.service.CategoryServiceModel;
import final_project.travel_agency.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryServiceModel>> getAllCategories() {
     List<CategoryServiceModel> allCategories = this.categoryService.getAllCategories();
      return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }


    @PostMapping(value = "/create")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryBindingModel categoryBindingModel) throws IOException {
        this.categoryService.createCategory(categoryBindingModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
