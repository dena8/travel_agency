package final_project.travel_agency.service.impl;


import final_project.travel_agency.model.binding.TourBindingModel;
import final_project.travel_agency.model.entity.Tour;
import final_project.travel_agency.model.service.CategoryServiceModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.repository.TourRepository;
import final_project.travel_agency.service.CategoryService;
import final_project.travel_agency.service.CloudinaryService;
import final_project.travel_agency.service.TourService;
import final_project.travel_agency.service.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    public TourServiceImpl(TourRepository tourRepository, ModelMapper modelMapper, CategoryService categoryService, UserService userService, CloudinaryService cloudinaryService) {
        this.tourRepository = tourRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }


    @Override
    public void createTour(TourBindingModel tour) throws NotFoundException, IOException {
        CategoryServiceModel categoryServiceModel = this.categoryService.getCategoryByName(tour.getCategory());
        UserServiceModel userServiceModel = getUser();
        LocalDateTime startedOn = getStartedOn(tour.getStartAndEnd());

        TourServiceModel tourServiceModel = this.modelMapper.map(tour,TourServiceModel.class);

        tourServiceModel.setCreator(userServiceModel);
        tourServiceModel.setCategory(categoryServiceModel);
        tourServiceModel.setImage(this.cloudinaryService.uploadImage(tour.getImage()));
        tourServiceModel.setStartedOn(startedOn);

        this.tourRepository.saveAndFlush(this.modelMapper.map(tourServiceModel, Tour.class));
    }

    @Override
    public TourServiceModel[] getAllTours() {
        return this.modelMapper.map(this.tourRepository.findAll(),TourServiceModel[].class);
    }

    @Override
    public TourServiceModel getTourById(String id) throws NotFoundException {
        Tour tour = this.tourRepository.findById(id).orElseThrow(() -> new NotFoundException("Tour not found"));
       return this.modelMapper.map(tour,TourServiceModel.class);
    }

    @Override
    public void deleteTour(String id) {
        this.tourRepository.deleteById(id);
    }

    @Override
    public void deathLineForTourRegistration() {
        this.tourRepository.stopTourRegistration(LocalDateTime.now());
    }


    private UserServiceModel getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return this.modelMapper.map(this.userService.loadUserByUsername(username), UserServiceModel.class);
    }

    private LocalDateTime getStartedOn(String startAndEnd) {
        String startDate = startAndEnd.substring(0, startAndEnd.indexOf("-"));
        DateTimeFormatter dtf = DateTimeFormatter
                .ofPattern("dd/MM/yy HH:mm:ss");
        return LocalDateTime.parse(startDate + " 00:00:00", dtf);
    }


}
