package final_project.travel_agency.service;

import final_project.travel_agency.model.binding.TourBindingModel;
import final_project.travel_agency.model.service.TourServiceModel;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
public interface TourService<T> {
    void createTour(TourBindingModel<T> tour) throws NotFoundException, IOException;

    TourServiceModel[] getAllTours();

    TourServiceModel getTourById(String id) throws NotFoundException;

    void deleteTour(String id) throws NotFoundException;

    int deathLineForTourRegistration(LocalDate date);

    void updateParticipants(String id);

    void resetParticipants(String tourId);

    int deleteExpiredTours(LocalDate date);

    void createUpdate(String id,TourBindingModel<T> tourBindingModel) throws NotFoundException, IOException;
}
