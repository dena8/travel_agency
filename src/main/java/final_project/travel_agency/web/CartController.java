package final_project.travel_agency.web;
import final_project.travel_agency.model.binding.UserBindingModel;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.service.OrderService;
import final_project.travel_agency.service.TourService;
import final_project.travel_agency.service.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final TourService tourService;
    private final UserService userService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;


    public CartController(TourService tourService, UserService userService, OrderService orderService, ModelMapper modelMapper) {
        this.tourService = tourService;
        this.userService = userService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add/{id}")
    public ResponseEntity<Void> addTourToCart(@PathVariable String id) throws Exception {
         this.userService.addTourToCart(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/contain/{id}")
    public ResponseEntity<Boolean> checkIfTourIsAdded(@PathVariable String id) throws NotFoundException {
        UserServiceModel authUser = this.userService.getAuthenticatedUser();
        boolean body = this.orderService.checkIfTourIsAdded(authUser,id);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<Void> createOrder(@RequestBody UserBindingModel userBindingModel) throws NotFoundException {
        UserServiceModel userServiceModel = this.modelMapper.map(userBindingModel,UserServiceModel.class);
        this.orderService.makeOrder(userServiceModel);
        this.userService.emptiedCard(userServiceModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @PutMapping("/remove-item")
    public ResponseEntity<Void> removeFromCart(@RequestParam("userId") String id, @RequestParam("tourId") String tourId) throws NotFoundException {
        this.userService.removeItemFromCart(id,tourId);
        this.tourService.resetParticipants(tourId);
        return ResponseEntity.ok().build();
    }

}
