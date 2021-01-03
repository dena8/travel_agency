package final_project.travel_agency.web;

import final_project.travel_agency.model.binding.UserRegisterBindingModel;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.model.view.UserViewModel;
import final_project.travel_agency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserViewModel> postRegister(@Valid @RequestBody UserRegisterBindingModel user){
        this.userService.register(this.modelMapper.map(user, UserServiceModel.class));
       return new ResponseEntity<>(HttpStatus.OK);
   }
}
