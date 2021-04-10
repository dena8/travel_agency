package final_project.travel_agency.web;


import final_project.travel_agency.model.entity.Category;
import final_project.travel_agency.model.entity.Tour;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.repository.CategoryRepository;
import final_project.travel_agency.repository.TourRepository;
import final_project.travel_agency.repository.UserRepository;
import final_project.travel_agency.service.UserService;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class TestData {


   private UserService userService;
   private UserRepository userRepository;
   private TourRepository tourRepository;
   private CategoryRepository categoryRepository;

    public TestData(UserService userService,UserRepository userRepository,TourRepository tourRepository, CategoryRepository categoryRepository){
        this.userService = userService;
        this.userRepository = userRepository;
        this.tourRepository=tourRepository;
        this.categoryRepository=categoryRepository;
    }

    public void init(){

    }


    public void clean(){
       // this.categoryRepository.deleteAll();
    }

    public void registerUsers(){
        UserServiceModel adminBindingModel = new UserServiceModel();
        adminBindingModel.setEmail("baselayer@abv.bg");
        adminBindingModel.setUsername("Pegasus");
        adminBindingModel.setPassword("pegasus");
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("Zajo");
        userServiceModel.setEmail("zajo@abv.bg");
        userServiceModel.setPassword("zajo");
       // userServiceModel.getAuthorities().add()
        UserServiceModel guideBindingModel = new UserServiceModel();
        guideBindingModel.setEmail("nims@abv.bg");
        guideBindingModel.setUsername("Nims");
        guideBindingModel.setPassword("nims");
        this.userService.register(adminBindingModel);
        this.userService.register(userServiceModel);
        this.userService.register(guideBindingModel);
    }

    public void createTour(){
        Category trekCategory = this.categoryRepository.findByName("Trek").orElseGet(null);
        Category alpinismCategory = this.categoryRepository.findByName("Alpinism").orElseGet(null);
        Tour botev = getTour(trekCategory,"http//image.jpg","Botev peak","asd fg","medium",new BigDecimal(55),4,"Balkan");
        Tour horse = getTour(alpinismCategory,"http//image1.jpg","Horse peak","asd fg","hard",new BigDecimal(250),3,"Pirin");
        tourRepository.saveAndFlush(botev);
        tourRepository.saveAndFlush(horse);
    }

    private Tour getTour(Category trekCategory, String image,String name,
                         String description, String difficultLevel,
                         BigDecimal price,Integer participants, String region) {
        User user = this.userRepository.findByUsername("Nims").orElseGet(null);
        Tour tour = new Tour();
        tour.setImage(image);
        tour.setName(name);
        tour.setCategory(trekCategory);
        tour.setDescription(description);
        tour.setDifficultyLevel(difficultLevel);
        tour.setPrice(price);
        tour.setParticipants(participants);
        tour.setRegion(region);
        tour.setStartDate(LocalDate.now().plusDays(3));
      //  tour.setCreator(user);
        return tour;
    }


}
