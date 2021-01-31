package final_project.travel_agency.web;

import final_project.travel_agency.model.binding.TourBindingModel;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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


    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createTour(@Valid @ModelAttribute("tour") TourBindingModel tour) throws NotFoundException, IOException {
        saveImageInStatic(tour);
        CategoryServiceModel categoryServiceModel = this.categoryService.getCategoryByName(tour.getCategory());
        UserServiceModel userServiceModel = getUser();
        TourServiceModel tourServiceModel= createTourServiceModel(tour, categoryServiceModel, userServiceModel);
        tourServiceModel.setImage("http://localhost:5000/image/"+tour.getImage().getOriginalFilename());
        this.tourService.createTour(tourServiceModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void saveImageInStatic(TourBindingModel tour) throws IOException {
        MultipartFile image = tour.getImage();
        Files.copy(image.getInputStream(),
                Paths.get("C:\\Users\\user\\Desktop\\travel_agency\\src\\main\\resources\\static\\image",image.getOriginalFilename()));
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
