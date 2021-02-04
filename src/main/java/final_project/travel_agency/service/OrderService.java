package final_project.travel_agency.service;

import final_project.travel_agency.model.entity.Order;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.OrderServiceModel;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {

    void makeOrder(Order order);

    void emptiedCard(User user) throws NotFoundException;

    List<OrderServiceModel> getAllOrders();

    OrderServiceModel getOrderById(String id);
}
