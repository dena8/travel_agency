package final_project.travel_agency.web;

import final_project.travel_agency.model.entity.Tour;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;

import final_project.travel_agency.service.TourService;
import final_project.travel_agency.service.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cart")
public class CartController {
    private final ModelMapper modelMapper;
    private final TourService tourService;
    private final UserService userService;

    public CartController(ModelMapper modelMapper, TourService tourService, UserService userService) {
        this.modelMapper = modelMapper;
        this.tourService = tourService;
        this.userService = userService;
    }

    @GetMapping("/add/{id}")
    public ResponseEntity<Void> addTourToCart(@PathVariable String id) throws Exception {
        TourServiceModel tourServiceModel = this.tourService.getTourById(id);
        if (tourServiceModel.getParticipants() < 1) {
            throw new Exception("No vacant places");
        }
        User user = this.modelMapper.map(getUser(), User.class);
        this.userService.addTourToCart(user, tourServiceModel);

        return new ResponseEntity<>(HttpStatus.OK);
    }

   // @PreAuthorize("hasAuthority('USER_ROLE')")
    //@PreAuthorize("hasAnyAuthority('USER_ROLE','GUIDE_ROLE','ADMIN_ROLE')")
    @GetMapping("/contain/{id}")
    public ResponseEntity<Boolean> checkIfTourIsAdded(@PathVariable String id) throws NotFoundException {
        User user = this.modelMapper.map(getUser(), User.class);
        Tour findT = user.getCart().stream().filter(t -> t.getId().equals(id)).findFirst()
                .orElse(null);
        boolean body = findT != null;
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    private UserServiceModel getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return this.modelMapper.map(this.userService.loadUserByUsername(username), UserServiceModel.class);
    }
}
