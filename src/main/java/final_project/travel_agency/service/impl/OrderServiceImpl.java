package final_project.travel_agency.service.impl;

import final_project.travel_agency.model.entity.Order;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.OrderServiceModel;
import final_project.travel_agency.repository.OrderRepository;
import final_project.travel_agency.repository.UserRepository;
import final_project.travel_agency.service.OrderService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void makeOrder(Order order) {
        System.out.println();
        this.orderRepository.saveAndFlush(order);
    }

    @Override
    public void emptiedCard(User currentUser) throws NotFoundException {
      User user=  this.userRepository.findByUsername(currentUser.getUsername()).orElseThrow(()-> new NotFoundException("no such user"));
      user.getCart().clear();
      userRepository.saveAndFlush(user);
    }

    @Override
    public List<OrderServiceModel> getAllOrders() {
        return List.of( this.modelMapper.map(this.orderRepository.findAll(),OrderServiceModel[].class));
    }

    @Override
    public OrderServiceModel getOrderById(String id) {
        Order order = this.orderRepository.findById(id).orElseThrow(()->new NoSuchElementException("Order not found"));
        System.out.println();
        return this.modelMapper.map(order,OrderServiceModel.class);
    }
}
