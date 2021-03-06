package final_project.travel_agency.service;

import final_project.travel_agency.model.binding.TourBindingModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.view.TourViewModel;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface TourService {
    void createTour(TourBindingModel tour) throws NotFoundException, IOException;

    TourServiceModel[] getAllTours();

    TourServiceModel getTourById(String id) throws NotFoundException;

    void deleteTour(String id) throws NotFoundException;

    void deathLineForTourRegistration(); 

    void updateParticipants(String id);

    void resetParticipants(String tourId);
}
