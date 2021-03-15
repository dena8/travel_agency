package final_project.travel_agency.event;

import final_project.travel_agency.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {
    private final EmailService emailService;

    public OrderEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener(OrderCreateEvent.class)
    public void OrderListener(OrderCreateEvent event){
        this.emailService.sendEmail("baselayer@abv.bg","Order information",String.format("You get order with id: %s",event.getOrderId()));
    }
}
