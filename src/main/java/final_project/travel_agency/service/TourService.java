package final_project.travel_agency.service;

import final_project.travel_agency.model.binding.TourBindingModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.view.TourViewModel;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface TourService {
    void createTour(TourServiceModel tourServiceModel);

    TourServiceModel[] getAllTours();

    TourServiceModel getTourById(String id) throws NotFoundException;
}
