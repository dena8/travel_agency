package final_project.travel_agency.service.impl;
import final_project.travel_agency.event.OrderEventPublisher;
import final_project.travel_agency.exception.NotCorrectDataEx;
import final_project.travel_agency.exception.NotFoundEx;
import final_project.travel_agency.model.entity.Order;
import final_project.travel_agency.model.entity.Tour;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.OrderServiceModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.repository.OrderRepository;
import final_project.travel_agency.repository.UserRepository;
import final_project.travel_agency.service.OrderService;
import final_project.travel_agency.service.TourService;
import final_project.travel_agency.service.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final OrderEventPublisher orderPublisher;
    private final UserService userService;
    private final TourService tourService;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ModelMapper modelMapper, OrderEventPublisher orderPublisher, UserService userService, TourService tourService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.orderPublisher = orderPublisher;
        this.userService = userService;
        this.tourService = tourService;
    }

    @Override
    public void makeOrder(User user) throws NotFoundException {
        Order order = createOrder(user);
        Order savedOrder =  this.orderRepository.saveAndFlush(order);
        this.orderPublisher.publishEvent(savedOrder.getId());
    }

    @Override
    public void emptiedCard(User currentUser) throws NotFoundException {
      User user=  this.userRepository.findByUsername(currentUser.getUsername()).orElseThrow(()-> new NotFoundEx("User not found"));
      user.getCart().clear();
      userRepository.saveAndFlush(user);
    }

    @Override
    public List<OrderServiceModel> getAllOrders() {
        return List.of( this.modelMapper.map(this.orderRepository.findAllByOrderByBuyDateAsc(),OrderServiceModel[].class));
    }

    @Override
    public OrderServiceModel getOrderById(String id) {
        Order order = this.orderRepository.findById(id).orElseThrow(()->new NoSuchElementException("Order not found"));
        return this.modelMapper.map(order,OrderServiceModel.class);
    }

    @Override
    public boolean checkIfTourIsAdded(UserServiceModel authUser,String id) {
        List<OrderServiceModel> orders = authUser.getOrders();
        for (OrderServiceModel order : orders) {
            int count = (int) order.getBuyingProducts().stream().filter(b -> b.getId().equals(id)).count();
            if (count > 0) {
                return true;
            }
        }

        TourServiceModel findT = authUser.getCart().stream().filter(t -> t.getId().equals(id)).findFirst()
                .orElse(null);
        return findT != null;
    }

    @Override
    public void addTourToCart(String id) throws Exception {
        TourServiceModel tourServiceModel = this.tourService.getTourById(id);
        if (tourServiceModel.getParticipants() < 1) {
            throw new NotCorrectDataEx("No vacant places");
        }
        UserServiceModel user = this.userService.getAuthenticatedUser();
        user.getCart().add(tourServiceModel);
        this.tourService.updateParticipants(tourServiceModel.getId());
        this.userRepository.saveAndFlush(this.modelMapper.map(user,User.class));
    }

    private Order createOrder(User user) throws NotFoundException {
        List<Tour> cart = new ArrayList<>(user.getCart());
        Order order = new Order();
        order.setBuyingProducts(cart);
        order.setCustomer(user);
        return order;
    }
}
