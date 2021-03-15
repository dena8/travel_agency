package final_project.travel_agency.service.impl;
import final_project.travel_agency.event.OrderEventPublisher;
import final_project.travel_agency.exception.NotFoundEx;
import final_project.travel_agency.model.entity.Order;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.OrderServiceModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
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
    private final OrderEventPublisher orderPublisher;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ModelMapper modelMapper, OrderEventPublisher orderPublisher) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.orderPublisher = orderPublisher;
    }

    @Override
    public void makeOrder(Order order) {
        System.out.println();
      Order newOrder =  this.orderRepository.saveAndFlush(order);
      this.orderPublisher.publishEvent(newOrder.getId());
    }

    @Override
    public void emptiedCard(User currentUser) throws NotFoundException {
      User user=  this.userRepository.findByUsername(currentUser.getUsername()).orElseThrow(()-> new NotFoundEx("User not found"));
      user.getCart().clear();
      userRepository.saveAndFlush(user);
    }

    @Override
    public List<OrderServiceModel> getAllOrders() {
//        Order[]orders = this.orderRepository.findAllByOrderByBuyDateAsc();
//        System.out.println();
        return List.of( this.modelMapper.map(this.orderRepository.findAllByOrderByBuyDateAsc(),OrderServiceModel[].class));
    }

    @Override
    public OrderServiceModel getOrderById(String id) {
        Order order = this.orderRepository.findById(id).orElseThrow(()->new NoSuchElementException("Order not found"));
        System.out.println();
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
}
