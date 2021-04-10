package final_project.travel_agency.service;

import final_project.travel_agency.model.service.OrderServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;


public interface OrderService {

    void makeOrder(UserServiceModel user) throws NotFoundException;

    List<OrderServiceModel> getAllOrders();

    Optional<OrderServiceModel> getOrderById(String id);

    boolean checkIfTourIsAdded(UserServiceModel authUser,String id);
}
