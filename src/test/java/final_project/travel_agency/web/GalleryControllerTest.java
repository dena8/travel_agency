package final_project.travel_agency.web;

import com.jayway.jsonpath.JsonPath;
import final_project.travel_agency.model.entity.Category;
import final_project.travel_agency.model.entity.Gallery;
import final_project.travel_agency.repository.CategoryRepository;
import final_project.travel_agency.repository.GalleryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class GalleryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GalleryRepository galleryRepository;

    @AfterEach
    private void tearDown() {
        this.galleryRepository.deleteAll();
    }

    @Test
    public void getAllCategories_should_return_valid_status() throws Exception {
        String description =
                "There are a million ways to travel, but few resonate in the way that trekking does. Get back to nature, challenge yourself and enter landscapes well beyond our urban environments. Picture yourself enjoying a spontaneous moment as you smile back at a nomadic mountain lady, snack by a rushing stream, follow winding paths that weave through the mountains, and scale rugged and rocky terrain in search of the perfect sunrise.";
        this.mockMvc.perform(MockMvcRequestBuilders.get("/galleries/view"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(description)));
    }
}
