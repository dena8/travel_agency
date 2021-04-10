package final_project.travel_agency.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import final_project.travel_agency.model.binding.UserBindingModel;
import final_project.travel_agency.model.binding.UserRegisterBindingModel;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private TourRepository tourRepository;
    private final String ADMIN_USERNAME = "Pegasus", ADMIN_PASSWORD = "pegasus", ADMIN_EMAIL = "baselayer@abv.bg";

    private TestData testData;

    @BeforeEach
    private void init() {
        this.testData = new TestData(this.userService,this.userRepository,this.tourRepository,this.categoryRepository);
    }

    @AfterEach
    private void tearDown() {
        this.userRepository.deleteAll();
    }

    @Test
    public void postRegister_should_work_correctly() throws Exception {
        UserRegisterBindingModel adminBindingModel = getUserRegisterBindingModel(ADMIN_USERNAME, ADMIN_EMAIL, ADMIN_PASSWORD);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminBindingModel))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{'username':'Pegasus','email':'baselayer@abv.bg'}"));
    }

    @Test
    public void postRegister_should_throw_exception_400() throws Exception {
        UserRegisterBindingModel adminBindingModel = getUserRegisterBindingModel("Pe", ADMIN_EMAIL, ADMIN_PASSWORD);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminBindingModel))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Provided data is not correct!")));
    }

    @Test
    public void postLogin_should_logged_in_user_correctly() throws Exception {
        this.testData.registerUsers();
        UserRegisterBindingModel adminBindingModel = getUserRegisterBindingModel(ADMIN_USERNAME, ADMIN_EMAIL, ADMIN_PASSWORD);


        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminBindingModel))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'username':'Pegasus'}"));

    }

    @Test
    public void postLogin_response_should_contain_authorization_header() throws Exception {
        this.testData.registerUsers();
        UserRegisterBindingModel adminBindingModel = getUserRegisterBindingModel(ADMIN_USERNAME, ADMIN_EMAIL, ADMIN_PASSWORD);


        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminBindingModel))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String header = mvcResult.getResponse().getHeader("Authorization");
        Assertions.assertNotNull(header, "Authorization header must not be null");
    }

    @Test
    public void postLogin_should_throw_exception_500() throws Exception {
        this.testData.registerUsers();
        UserRegisterBindingModel adminBindingModel = getUserRegisterBindingModel("Pe", null, ADMIN_PASSWORD);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminBindingModel))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("No such user exist")));
    }

    @Test
    @WithMockUser(value = "Zajo", authorities = "USER_ROLE")
    public void getCurrentUser_should_return_correct() throws Exception {
        this.testData.registerUsers();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/get/current"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("Zajo")));
    }

    @Test
    @WithMockUser(value = "Not existing", authorities = "USER_ROLE")
    public void getCurrentUser_should_throw_404() throws Exception {
        this.testData.registerUsers();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/get/current"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No such user exist")));
    }

    @Test
    @WithMockUser(value = "Nims", authorities = "GUIDE_ROLE")
    public void getCurrentUser_when_unauthorized_should_throw_500() throws Exception {
        this.testData.registerUsers();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/get/current"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Access is denied")));
    }

    @Test
    @WithMockUser(value = "Nims", authorities = "GUIDE_ROLE")
    public void getAuthorityNames_should_work_correctly() throws Exception {
        this.testData.registerUsers();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/authorities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0]", is("ADMIN_ROLE")))
                .andExpect(jsonPath("$[1]", is("USER_ROLE")))
                .andExpect(jsonPath("$[2]", is("GUIDE_ROLE")));

    }

    @Test
    @WithMockUser(value = "Zajo", authorities = "USER_ROLE")
    public void getAuthorityNames_when_unauthorized_should_throw_500() throws Exception {
        this.testData.registerUsers();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/authorities"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Access is denied")));
    }

    @Test
    public void getAuthorityNames_when_notLoggedIn_should_throw_500() throws Exception {
        this.testData.registerUsers();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/authorities"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Access is denied")));
    }

    @Test
    @WithMockUser(value = "Pegasus", authorities = "ADMIN_ROLE")
    public void updateAuthority_should_work_correctly() throws Exception {
        UserBindingModel userBindingModel = new UserBindingModel();
        userBindingModel.setUsername("Zajo");
        userBindingModel.setAuthority("GUIDE_ROLE");
        this.testData.registerUsers();
        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/update/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userBindingModel))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("Zajo")));

        Assertions.assertEquals("GUIDE_ROLE", this.userRepository.findByUsername("Zajo").get().getAuthorities().get(0).getAuthority());
    }

    @Test
    @WithMockUser(value = "Pegasus", authorities = "ADMIN_ROLE")
    public void updateAuthority_should_throw_404() throws Exception {
        UserBindingModel userBindingModel = new UserBindingModel();
        userBindingModel.setUsername("NotExist");
        userBindingModel.setAuthority("GUIDE_ROLE");
        this.testData.registerUsers();

        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/update/authority"))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void findOneByName_should_work_correctly() throws Exception {
        this.testData.registerUsers();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/find").param("username", "Nims"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));

    }


    private UserRegisterBindingModel getUserRegisterBindingModel(String username, String email, String password) {
        UserRegisterBindingModel registerBindingModel = new UserRegisterBindingModel();
        registerBindingModel.setEmail(email);
        registerBindingModel.setUsername(username);
        registerBindingModel.setPassword(password);
        return registerBindingModel;
    }

}
