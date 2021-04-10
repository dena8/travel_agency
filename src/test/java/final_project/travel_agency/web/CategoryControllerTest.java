package final_project.travel_agency.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import final_project.travel_agency.model.binding.CategoryBindingModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(value = "pesho", authorities = "GUIDE_ROLE")
    public void getAllCategories_should_work_correctly() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Alpinism")))
                .andExpect(jsonPath("$[1].name", is("Trek")));
    }

    @Test
    @WithMockUser(value = "pesho", authorities = "GUIDE_ROLE")
    public void createCategory_should_work_correctly() throws Exception {
        CategoryBindingModel categoryBindingModel = new CategoryBindingModel();
        categoryBindingModel.setName("Caveing");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/categories/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryBindingModel))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}
