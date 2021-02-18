package final_project.travel_agency.web;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


@RestController
@RequestMapping("/tours")
public class TourController {
    private final ModelMapper modelMapper;
    private final TourService tourService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final Cloudinary cloudinary;

    public TourController(ModelMapper modelMapper, TourService tourService, CategoryService categoryService, UserService userService, Cloudinary cloudinary) {
        this.modelMapper = modelMapper;
        this.tourService = tourService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.cloudinary = cloudinary;
    }


    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createTour(@Valid @ModelAttribute("tour") TourBindingModel tour) throws NotFoundException, IOException, ParseException {

        Map uploadResult = cloudinary.uploader().upload(tour.getImage().getBytes(), ObjectUtils.emptyMap());

        CategoryServiceModel categoryServiceModel = this.categoryService.getCategoryByName(tour.getCategory());
        UserServiceModel userServiceModel = getUser();
        TourServiceModel tourServiceModel = createTourServiceModel(tour, categoryServiceModel, userServiceModel);
        String url = String.valueOf(uploadResult.get("url"));
        tourServiceModel.setImage(url);
        LocalDateTime startedOn = getStartedOn(tour.getStartAndEnd());
        tourServiceModel.setStartedOn(startedOn);
        this.tourService.createTour(tourServiceModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private LocalDateTime getStartedOn(String startAndEnd) {
        String startDate = startAndEnd.substring(0, startAndEnd.indexOf("-"));
        DateTimeFormatter dtf = DateTimeFormatter
                .ofPattern("dd/MM/yy HH:mm:ss");
//        LocalDateTime asd =  LocalDateTime.parse(startDate+" 00:00:00",dtf);
//        boolean search = asd.isBefore(asd.minusDays(3));
//
//        System.out.println(asd);
        return LocalDateTime.parse(startDate + " 00:00:00", dtf);
    }

    @GetMapping("/all")
    public ResponseEntity<TourViewModel[]> getAllTours() {
        TourServiceModel[] tourServiceModels = this.tourService.getAllTours();
        return new ResponseEntity<>(this.modelMapper.map(tourServiceModels, TourViewModel[].class), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourViewModel> getTour(@PathVariable String id) throws NotFoundException {
        TourServiceModel tourServiceModel = this.tourService.getTourById(id);
        return new ResponseEntity<>(this.modelMapper.map(tourServiceModel, TourViewModel.class), HttpStatus.OK);
    }

    @GetMapping("/remove/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable String id) {
        this.tourService.deleteTour(id);
        return null;
    }

    private TourServiceModel createTourServiceModel(@RequestBody @Valid TourBindingModel tour, CategoryServiceModel categoryServiceModel, UserServiceModel userServiceModel) {
        TourServiceModel tourServiceModel = this.modelMapper.map(tour, TourServiceModel.class);
        tourServiceModel.setCreator(userServiceModel);
        tourServiceModel.setCategory(categoryServiceModel);
        return tourServiceModel;
    }

    private UserServiceModel getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return this.modelMapper.map(this.userService.loadUserByUsername(username), UserServiceModel.class);
    }


}
