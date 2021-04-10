package final_project.travel_agency;

import final_project.travel_agency.event.OrderEventPublisher;
import final_project.travel_agency.model.entity.Order;
import final_project.travel_agency.model.service.OrderServiceModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.repository.OrderRepository;
import final_project.travel_agency.service.OrderService;
import final_project.travel_agency.service.UserService;
import final_project.travel_agency.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository mockRepository;

    private OrderEventPublisher orderPublisher;

    private OrderService serviceToTest;
    private Order order1,order2;
    private OrderServiceModel orderUuid1, orderUuid2;
    @Mock
    private UserService mockUserService;
    private UserServiceModel authUser;




    @BeforeEach
    private void init(){
        this.order1 = new Order();
        this.order1.setId("uuid1");
        this.order2 = new Order();
        this.order2.setId("uuid2");
        this.orderUuid1 = new OrderServiceModel();
        orderUuid1.setId("uuid1");
        TourServiceModel tour = new TourServiceModel();
        tour.setId("tourId");
        orderUuid1.getBuyingProducts().add(tour);
        this.orderUuid2 = new OrderServiceModel();
        orderUuid2.setId("uuid2");
        this.authUser = new UserServiceModel();
        authUser.setId("auth");
        List<OrderServiceModel> orders = new ArrayList<>();
        orders.add(orderUuid1);
        orders.add(orderUuid2);
        authUser.setOrders(orders);

        this.serviceToTest = new OrderServiceImpl(mockRepository,mockUserService ,new ModelMapper(), orderPublisher);
    }

    @Test
    public void getOrderById_should_return_correct_result(){
        when(mockRepository.findById("uuid1")).thenReturn(Optional.of(order1));
        Optional<OrderServiceModel> result=  this.serviceToTest.getOrderById("uuid1");
        assert result.isPresent();
        Assertions.assertEquals(orderUuid1.getId(),result.get().getId());

    }

    @Test
    public void getOrderById_Throw_Ex_If_OrderIsNotFound(){
        when(mockRepository.findById("wrongId")).thenThrow(new NoSuchElementException("Order"));
        Assertions.assertThrows(NoSuchElementException.class,()->{this.serviceToTest.getOrderById("wrongId");});
    }

    @Test
    public void getAllOrders_should_return_all_orders(){
       List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        when(this.mockRepository.findAllByOrderByBuyDateAsc()).thenReturn(orders);

        List<OrderServiceModel> result =  this.serviceToTest.getAllOrders();

        Assertions.assertEquals(2,result.size());
        Assertions.assertEquals(orderUuid1.getId(),result.get(0).getId());
        Assertions.assertEquals(orderUuid2.getId(),result.get(1).getId());

    }

    @Test
    public void checkIfTourIsAdded_should_return_true(){
         boolean result = this.serviceToTest.checkIfTourIsAdded(authUser,"tourId");
         Assertions.assertTrue(result);
    }

    @Test
    public void checkIfTourIsAdded_should_return_false(){
        boolean result = this.serviceToTest.checkIfTourIsAdded(authUser,"thisTourIsNotOrdered");
        Assertions.assertFalse(result);
    }
}
