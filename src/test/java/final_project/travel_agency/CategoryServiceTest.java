package final_project.travel_agency;


import final_project.travel_agency.model.entity.Category;
import final_project.travel_agency.model.service.CategoryServiceModel;
import final_project.travel_agency.repository.CategoryRepository;
import final_project.travel_agency.service.impl.CategoryServiceImpl;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository mockRepository;
    private Category trekCategory, alpinismCategory;
    private CategoryServiceModel trekServiceModel, alpinismServiceModel;

    private CategoryServiceImpl serviceToTest;

    @BeforeEach
    private void init(){
      this.trekCategory = new Category();
      this.trekCategory.setName("Trek");

      this.alpinismCategory = new Category();
      this.alpinismCategory.setName("Alpinism");

      this.trekServiceModel = new CategoryServiceModel();
      this.trekServiceModel.setName("Trek");

      this.alpinismServiceModel = new CategoryServiceModel();
      this.alpinismServiceModel.setName("Alpinism");

       serviceToTest = new CategoryServiceImpl(mockRepository, new ModelMapper());
    }

    @Test
    public void testGetCategoryByName() throws NotFoundException {
       when(mockRepository.findByName("Trek")).thenReturn(java.util.Optional.ofNullable(trekCategory));
        CategoryServiceModel result = serviceToTest.getCategoryByName("Trek");

        Assert.assertNotNull(result);
        Assert.assertEquals("trekBindingModel expect to be equal to result",result.getName(),trekServiceModel.getName() );
    }

    @Test
    public void getAllCategoriesTest(){
        when(mockRepository.findAll()).thenReturn(List.of(trekCategory,alpinismCategory));
        List<CategoryServiceModel> result = serviceToTest.getAllCategories();
        List<CategoryServiceModel> actual = List.of(trekServiceModel,alpinismServiceModel);
        Assert.assertEquals(2,result.size());
        Assert.assertEquals(actual.get(0).getName(),result.get(0).getName());
        Assert.assertEquals(actual.get(1).getName(),result.get(1).getName());
    }
}
