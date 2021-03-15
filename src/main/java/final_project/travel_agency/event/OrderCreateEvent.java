package final_project.travel_agency.event;

public class OrderCreateEvent {
    private String orderId;

    public OrderCreateEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
