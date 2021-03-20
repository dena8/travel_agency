package final_project.travel_agency.service.impl;

import final_project.travel_agency.exception.NotFoundEx;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;


@Service
public class TourServiceImpl<T> implements TourService<T> {
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
    public void createTour(TourBindingModel<T> tour) throws NotFoundException, IOException {
        TourServiceModel tourServiceModel = getTourServiceModel(tour);
        tourServiceModel.setImage(this.cloudinaryService.uploadImage((MultipartFile) tour.getImage()));
        this.tourRepository.saveAndFlush(this.modelMapper.map(tourServiceModel, Tour.class));
    }

    @Override
    public TourServiceModel[] getAllTours() {
        return this.modelMapper.map(this.tourRepository.findAllEnabledTours(), TourServiceModel[].class);
    }

    @Override
    public TourServiceModel getTourById(String id) {
        Tour tour = this.tourRepository.findById(id).orElseThrow(() -> new NotFoundEx("Tour not found"));
        return this.modelMapper.map(tour, TourServiceModel.class);
    }

    @Override
    public void deleteTour(String id) {
        this.tourRepository.enabledTour(id);
    }

    @Override
    public int deathLineForTourRegistration(LocalDate date) {
        return this.tourRepository.stopTourRegistration(LocalDate.now().plusDays(3));
    }

    @Override
    public void updateParticipants(String id) {
        this.tourRepository.updateParticipants(id);
    }

    @Override
    public void resetParticipants(String tourId) {
        this.tourRepository.resetParticipants(tourId);
    }

    @Override
    public int deleteExpiredTours(LocalDate date) {
        return this.tourRepository.deleteExpiredTour(date);
    }

    @Override
    public void createUpdate(String id, TourBindingModel<T> tour) throws NotFoundException, IOException {
        TourServiceModel tourServiceModel = getTourServiceModel(tour);
        tourServiceModel.setId(id);
        if (tour.getImage() instanceof MultipartFile) {
            tourServiceModel.setImage(this.cloudinaryService.uploadImage((MultipartFile) tour.getImage()));
        }
        this.tourRepository.saveAndFlush(this.modelMapper.map(tourServiceModel, Tour.class));
    }

    private TourServiceModel getTourServiceModel(TourBindingModel<T> tour) throws NotFoundException {
        CategoryServiceModel categoryServiceModel = this.categoryService.getCategoryByName(tour.getCategory());
        UserServiceModel userServiceModel = this.userService.getAuthenticatedUser();
        TourServiceModel tourServiceModel = this.modelMapper.map(tour, TourServiceModel.class);

        tourServiceModel.setCreator(userServiceModel);
        tourServiceModel.setCategory(categoryServiceModel);
        tourServiceModel.setEnabled(true);
        return tourServiceModel;
    }


}