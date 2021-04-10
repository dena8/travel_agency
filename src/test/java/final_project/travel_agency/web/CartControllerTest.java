package final_project.travel_agency.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import final_project.travel_agency.model.entity.User;
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
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

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
    @Transactional
    @WithMockUser(value = "Zajo", authorities = "USER_ROLE")
    public void addTourToCart_should_work_correctly() throws Exception {
        String id = this.tourRepository.findAll().get(0).getId();
         this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/add/{id}",id))
                .andExpect(status().isOk());

        String result = this.userRepository.findByUsername("Zajo").get().getCart().get(0).getId();
        Assertions.assertEquals(id,result);
    }

    @Test
    @Transactional
    @WithMockUser(value = "Zajo", authorities = "USER_ROLE")
    public void addTourToCart_ifTourIdIsNotValid_should_throw_ex_404() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/add/{id}","notValidId"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Tour not found")));
    }

    @Test
    @Transactional
    @WithMockUser(value = "NotExist", authorities = "USER_ROLE")
    public void addTourToCart_ifUserNotExist_should_throw_notFound404() throws Exception {
        String id = this.tourRepository.findAll().get(0).getId();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/add/{id}",id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No such user exist")));
    }

    @Test
    @Transactional
    @WithMockUser(value = "Zajo", authorities = "USER_ROLE")
    public void checkIfTourIsAdded_ifFalseCondition_should_work_correctly() throws Exception {
        String id = this.tourRepository.findAll().get(0).getId();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/contain/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @Transactional
    @WithMockUser(value = "Zajo", authorities = "USER_ROLE")
    public void checkIfTourIsAdded_ifTrueCondition_should_work_correctly() throws Exception {
        User zajo = this.userRepository.findByUsername("Zajo").orElseGet(null);
        zajo.getCart().add(this.tourRepository.findAll().get(0));
        this.userRepository.saveAndFlush(zajo);
        String id = this.tourRepository.findAll().get(0).getId();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/contain/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @Transactional
    @WithMockUser(value = "notValidUserId", authorities = "USER_ROLE")
    public void checkIfTourIsAdded_ifUserIdIsNotValid_should_throw_ex_404() throws Exception {
        String id = this.tourRepository.findAll().get(0).getId();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/contain/{id}",id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No such user exist")));
    }


}
