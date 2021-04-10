package final_project.travel_agency.web;

import final_project.travel_agency.repository.CategoryRepository;
import final_project.travel_agency.repository.TourRepository;
import final_project.travel_agency.repository.UserRepository;
import final_project.travel_agency.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TourControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    private TestData testData;

    @BeforeEach
    private void init(){
        this.testData = new TestData(this.userService,this.userRepository,this.tourRepository,this.categoryRepository);
        this.testData.registerUsers();
        this.testData.createTour();
    }

    @AfterEach
    private void tearDown(){
        this.userRepository.deleteAll();
        this.tourRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "Nims", authorities = "GUIDE_ROLE")
    public void getAuthorityNames_should_work_correctly() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/tours/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Botev peak")))
                .andExpect(jsonPath("$[1].name", is("Horse peak")));
    }

    @Test
    @WithMockUser(value = "Nims", authorities = "GUIDE_ROLE")
    public void getTour_should_work_correctly() throws Exception {
      String id = this.tourRepository.findAll().get(0).getId();

        this.mockMvc.perform(MockMvcRequestBuilders.get("/tours/{1d}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Botev peak")));
    }

    @Test
    @WithMockUser(value = "Nims", authorities = "GUIDE_ROLE")
    public void getTour_throw_notFound_ex_404() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/tours/{1d}","notExistingId"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Tour not found")));
    }

    @Test
    @WithMockUser(value = "Pegasus", authorities = "ADMIN_ROLE")
    public void removeTour_should_work_correctly() throws Exception {
        String id = this.tourRepository.findAll().get(0).getId();

        this.mockMvc.perform(MockMvcRequestBuilders.get("/tours/remove/{id}",id))
                .andExpect(status().isOk());

        Assertions.assertEquals(2, this.tourRepository.count());
        Assertions.assertEquals(true, this.tourRepository.findAll().get(0).getEnabled());

    }

    @Test
    @WithMockUser(value = "Nims", authorities = "GUIDE_ROLE")
    public void removeTour_when_anouthorized_throw_ex_500() throws Exception {
        String id = this.tourRepository.findAll().get(0).getId();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/tours/remove/{id}",id))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Access is denied")));
    }


}


