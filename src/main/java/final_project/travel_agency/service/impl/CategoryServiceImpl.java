package final_project.travel_agency.service.impl;

import final_project.travel_agency.model.entity.Category;
import final_project.travel_agency.model.service.CategoryServiceModel;
import final_project.travel_agency.repository.CategoryRepository;
import final_project.travel_agency.service.CategoryService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CategoryServiceModel> getAllCategories() {
        return this.categoryRepository.findAll()
                .stream()
                .map(c->this.modelMapper.map(c,CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel getCategoryByName(String name) throws NotFoundException {
        Category category= this.categoryRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        return this.modelMapper.map(category,CategoryServiceModel.class);
    }


}
