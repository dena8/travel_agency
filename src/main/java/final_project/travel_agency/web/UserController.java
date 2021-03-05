package final_project.travel_agency.web;

import com.google.gson.Gson;
import final_project.travel_agency.model.binding.UserRegisterBindingModel;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.model.view.CurrentUserViewModel;
import final_project.travel_agency.model.view.UserViewModel;
import final_project.travel_agency.service.UserService;
import final_project.travel_agency.util.jwt.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final Gson gson;

    public UserController(ModelMapper modelMapper, UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, Gson gson) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.gson = gson;
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<UserViewModel> postRegister(@Valid @RequestBody UserRegisterBindingModel user) {
        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        this.userService.register(userServiceModel);
        return new ResponseEntity<>(this.modelMapper.map(userServiceModel,UserViewModel.class),HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> postLogin(@RequestBody UserRegisterBindingModel userRegisterBindingModel) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRegisterBindingModel.getUsername(),userRegisterBindingModel.getPassword()));
        String token = this.jwtUtil.generateToken(this.userService.loadUserByUsername(userRegisterBindingModel.getUsername()));
        System.out.println("Bearer "+ token);
        HttpHeaders headers = createAuthorizationHeader(token);
        return new ResponseEntity<>(gson.toJson("Successful login"),headers, HttpStatus.OK);
    }

    @GetMapping("/get/current")
    public ResponseEntity<CurrentUserViewModel> getCurrentUser(){
       CurrentUserViewModel user = this.modelMapper.map(this.userService.getAuthenticatedUser(),CurrentUserViewModel.class);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }



    private HttpHeaders createAuthorizationHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        return headers;
    }


}
