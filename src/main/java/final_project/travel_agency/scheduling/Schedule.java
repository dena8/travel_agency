package final_project.travel_agency.scheduling;


import final_project.travel_agency.service.LogService;
import final_project.travel_agency.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Schedule {

    private final TourService tourService;
    private final LogService logService;
    private final Logger logger = LoggerFactory.getLogger(Schedule.class);


    public Schedule(TourService tourService, LogService logService) {
        this.tourService = tourService;
        this.logService = logService;
    }


    @Scheduled(cron = "0 10 0 * * ?")
    public void stopTourRegistration() {
        int count = this.tourService.deathLineForTourRegistration(LocalDate.now().plusDays(3));
        logger.info("Every day in 0:10 AM setting participants to zero, for every tour which start 3 days after and have vacant place. Updated {} tours", count);
    }

    @Scheduled(cron = "0 58 13 * * ?")
    public void deleteLogs() {
     int count = this.logService.deleteLogs();
       logger.info("After the end of the working day delete logs. Count deleted logs - {}", count);
    }

    @Scheduled(fixedRate = 86400000, initialDelay = 1000)
    public void deleteExpiredTours() {
        int count = this.tourService.deleteExpiredTours(LocalDate.now());
        logger.info("Every 24 hours delete all tours, which expired in the same day. Deleted {} tours.", count);

    }
}
