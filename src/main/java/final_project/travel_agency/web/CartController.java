package final_project.travel_agency.web;
import final_project.travel_agency.model.binding.UserBindingModel;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.service.OrderService;
import final_project.travel_agency.service.TourService;
import final_project.travel_agency.service.UserService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
   // private final ModelMapper modelMapper;
    private final TourService tourService;
    private final UserService userService;
    private final OrderService orderService;


    public CartController( TourService tourService, UserService userService, OrderService orderService) {
       // this.modelMapper = modelMapper;
        this.tourService = tourService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/add/{id}")
    public ResponseEntity<Void> addTourToCart(@PathVariable String id) throws Exception {
        this.orderService.addTourToCart(id);
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
        User user = (User) this.userService.loadUserByUsername(userBindingModel.getUsername());
        this.orderService.makeOrder(user);
        this.orderService.emptiedCard(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @PutMapping("/remove-item")
    public ResponseEntity<Void> removeFromCart(@RequestParam("userId") String id, @RequestParam("tourId") String tourId) throws NotFoundException {
        this.userService.removeItemFromCart(id,tourId);
        this.tourService.resetParticipants(tourId);
        return ResponseEntity.ok().build();
    }

}
