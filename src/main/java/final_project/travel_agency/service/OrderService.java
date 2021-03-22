package final_project.travel_agency.service;

import final_project.travel_agency.model.entity.Order;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.OrderServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {

    void makeOrder(User user) throws NotFoundException;

    void emptiedCard(User user) throws NotFoundException;

    List<OrderServiceModel> getAllOrders();

    OrderServiceModel getOrderById(String id);

    boolean checkIfTourIsAdded(UserServiceModel authUser,String id);
}
