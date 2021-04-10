package final_project.travel_agency.service.impl;
import final_project.travel_agency.event.OrderEventPublisher;
import final_project.travel_agency.model.entity.Order;
import final_project.travel_agency.model.entity.Tour;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.OrderServiceModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.repository.OrderRepository;
import final_project.travel_agency.service.OrderService;
import final_project.travel_agency.service.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final OrderEventPublisher orderPublisher;


    public OrderServiceImpl(OrderRepository orderRepository,UserService userService, ModelMapper modelMapper, OrderEventPublisher orderPublisher) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.orderPublisher = orderPublisher;
        this.userService = userService;
    }

    @Override
    public void makeOrder(UserServiceModel userServiceModel) throws NotFoundException {
        User user = (User) this.userService.loadUserByUsername(userServiceModel.getUsername());
        Order order = createOrder(user);
        Order savedOrder =  this.orderRepository.saveAndFlush(order);
        this.orderPublisher.publishEvent(savedOrder.getId());
    }

    @Override
    public List<OrderServiceModel> getAllOrders() {
        return List.of( this.modelMapper.map(this.orderRepository.findAllByOrderByBuyDateAsc(),OrderServiceModel[].class));
    }

    @Override
    public Optional<OrderServiceModel> getOrderById(String id) {
        Order order = this.orderRepository.findById(id).orElseThrow(()->new NoSuchElementException("No such order found"));//
        return Optional.of( this.modelMapper.map(order,OrderServiceModel.class));
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

    private Order createOrder(User user) throws NotFoundException {
        List<Tour> cart = new ArrayList<>(user.getCart());
        Order order = new Order();
        order.setBuyingProducts(cart);
        order.setCustomer(user);
        return order;
    }
}
