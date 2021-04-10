package final_project.travel_agency.web;

import final_project.travel_agency.model.binding.CategoryBindingModel;

import final_project.travel_agency.model.view.CategoryViewModel;
import final_project.travel_agency.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;


    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryViewModel>> getAllCategories() {
        List<CategoryViewModel> allCategories = this.categoryService.getAllCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryBindingModel categoryBindingModel) throws IOException {
        this.categoryService.createCategory(categoryBindingModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
