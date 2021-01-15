package final_project.travel_agency.service.impl;

import final_project.travel_agency.model.binding.TourBindingModel;
import final_project.travel_agency.model.entity.Category;
import final_project.travel_agency.model.entity.Tour;
import final_project.travel_agency.model.service.CategoryServiceModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.repository.CategoryRepository;
import final_project.travel_agency.repository.TourRepository;
import final_project.travel_agency.service.TourService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;
    private final ModelMapper modelMapper;

    public TourServiceImpl(TourRepository tourRepository, ModelMapper modelMapper) {
        this.tourRepository = tourRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void createTour(TourServiceModel tourServiceModel) {
        this.tourRepository.saveAndFlush(this.modelMapper.map(tourServiceModel, Tour.class));
    }
}
