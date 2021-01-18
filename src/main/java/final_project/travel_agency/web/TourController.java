package final_project.travel_agency.web;

import final_project.travel_agency.model.binding.TourBindingModel;
import final_project.travel_agency.model.entity.Tour;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.CategoryServiceModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.model.view.TourViewModel;
import final_project.travel_agency.service.CategoryService;
import final_project.travel_agency.service.TourService;
import final_project.travel_agency.service.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tours")
public class TourController {
    private final ModelMapper modelMapper;
    private final TourService tourService;
    private final CategoryService categoryService;
    private final UserService userService;

    public TourController(ModelMapper modelMapper, TourService tourService, CategoryService categoryService, UserService userService) {
        this.modelMapper = modelMapper;
        this.tourService = tourService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createTour(@Valid @RequestBody TourBindingModel tour) throws NotFoundException {
        CategoryServiceModel categoryServiceModel = this.categoryService.getCategoryByName(tour.getCategory());
        UserServiceModel userServiceModel = getUser();
        TourServiceModel tourServiceModel= createTourServiceModel(tour, categoryServiceModel, userServiceModel);
        this.tourService.createTour(tourServiceModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<TourViewModel[]> getAllTours(){
      TourServiceModel [] tourServiceModels= this.tourService.getAllTours();
        return new ResponseEntity<>(this.modelMapper.map(tourServiceModels,TourViewModel[].class), HttpStatus.OK) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourViewModel> getTour( @PathVariable String id) throws NotFoundException {
        TourServiceModel tourServiceModel= this.tourService.getTourById(id);
        return new ResponseEntity<>(this.modelMapper.map(tourServiceModel,TourViewModel.class),HttpStatus.OK);
    }

    private TourServiceModel createTourServiceModel(@RequestBody @Valid TourBindingModel tour, CategoryServiceModel categoryServiceModel, UserServiceModel userServiceModel) {
        TourServiceModel tourServiceModel = this.modelMapper.map(tour,TourServiceModel.class);
        tourServiceModel.setCreator(userServiceModel);
        tourServiceModel.setCategory(categoryServiceModel);
        return tourServiceModel;
    }

    private UserServiceModel getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return this.modelMapper.map(this.userService.loadUserByUsername(username),UserServiceModel.class);
    }
}
