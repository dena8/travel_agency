package final_project.travel_agency.event;

import final_project.travel_agency.service.EmailService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {
    private final ApplicationEventPublisher orderPublisher;


    public OrderEventPublisher(ApplicationEventPublisher orderPublisher) {
        this.orderPublisher = orderPublisher;

    }

   public void publishEvent(String orderId) {
       OrderCreateEvent orderEvent = new OrderCreateEvent(orderId);
       orderPublisher.publishEvent(orderEvent);
    }
}
