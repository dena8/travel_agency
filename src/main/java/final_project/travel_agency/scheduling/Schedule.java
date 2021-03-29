package final_project.travel_agency.scheduling;


import final_project.travel_agency.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class Schedule {

    private final TourService tourService;
    private final Logger logger = LoggerFactory.getLogger(Schedule.class);


    public Schedule(TourService tourService) {
        this.tourService = tourService;
    }


    @Scheduled(cron = "0 10 0 * * ?")
    public void stopTourRegistration() {
        int count = this.tourService.deathLineForTourRegistration(LocalDate.now().plusDays(3));
        logger.info("Updated {} tours", count);

    }

    @Scheduled(cron = "0 32 15 * * ?")
    public void stopTourRegistrationTest() {
        int count = this.tourService.deathLineForTourRegistration(LocalDate.now().plusDays(3));
        logger.info("Updated {} tours", count);

    }

    @Scheduled(fixedRate = 86400000, initialDelay = 60000)
    public void deleteExpiredTours() {
        logger.info("Every 24 hours delete all tours, which expired in the same day");
        int count = this.tourService.deleteExpiredTours(LocalDate.now());
        logger.info("Deleted {} tours", count);

    }
}
