package final_project.travel_agency.service;

import final_project.travel_agency.model.binding.CategoryBindingModel;
import final_project.travel_agency.model.service.CategoryServiceModel;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
   List<CategoryServiceModel> getAllCategories();
   CategoryServiceModel getCategoryByName(String name) throws NotFoundException;

    void createCategory(CategoryBindingModel categoryBindingModel);
}
