package final_project.travel_agency.web;

import final_project.travel_agency.model.service.OrderServiceModel;
import final_project.travel_agency.model.view.OrdersViewModel;
import final_project.travel_agency.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrdersViewModel>> getAllOrders(){
       List<OrderServiceModel> orderServiceModels= this.orderService.getAllOrders();
       List<OrdersViewModel> ordersViewModelList = Arrays.stream(this.modelMapper.map(orderServiceModels, OrdersViewModel[].class)).collect(Collectors.toList());
       return new ResponseEntity<>(ordersViewModelList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdersViewModel> getOrder(@PathVariable("id") String id){
        OrdersViewModel ordersViewModel = modelMapper.map(this.orderService.getOrderById(id),OrdersViewModel.class);
        return new ResponseEntity<>(ordersViewModel,HttpStatus.OK);
    }

}
