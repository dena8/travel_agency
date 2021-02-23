package final_project.travel_agency.scheduling;


import final_project.travel_agency.service.TourService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Schedule {

    private final TourService tourService;


    public Schedule(TourService tourService) {
        this.tourService = tourService;
    }

  //  @Scheduled(fixedRate =60*60*1000 )
    public void doSomething(){
        this.tourService.deathLineForTourRegistration();
        System.out.println("At 01:00:00am every day");
        //"0 56 7 * * ?"
    }
}
