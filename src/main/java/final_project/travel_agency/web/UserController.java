package final_project.travel_agency.web;

import final_project.travel_agency.model.binding.UserBindingModel;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;



    public UserController(ModelMapper modelMapper, UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<UserViewModel> postRegister(@Valid @RequestBody UserRegisterBindingModel user) {
        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        this.userService.register(userServiceModel);
        return new ResponseEntity<>(this.modelMapper.map(userServiceModel, UserViewModel.class), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<UserViewModel> postLogin(@RequestBody UserRegisterBindingModel userRegisterBindingModel) throws Exception {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRegisterBindingModel.getUsername(), userRegisterBindingModel.getPassword()));
        String token = this.jwtUtil.generateToken(this.userService.loadUserByUsername(userRegisterBindingModel.getUsername()));
        System.out.println("Bearer " + token);
        HttpHeaders headers = createAuthorizationHeader(token);
        UserViewModel user = new UserViewModel();
        user.setUsername(userRegisterBindingModel.getUsername());

        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER_ROLE')")
    @GetMapping("/get/current")
    public ResponseEntity<CurrentUserViewModel> getCurrentUser() {
        CurrentUserViewModel user = this.modelMapper.map(this.userService.getAuthenticatedUser(), CurrentUserViewModel.class);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/authorities")
    public ResponseEntity<List<String>> getAuthorityNames() {
        List<String> authorityNames = this.userService.getAuthorityNames();
        return ResponseEntity.ok().body(authorityNames);

    }

    @PutMapping("/update/authority")
    public ResponseEntity<Void> updateAuthority(@RequestBody UserBindingModel userBindingModel) {
        this.userService.updateAuthority(userBindingModel);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find")
    public ResponseEntity<Boolean> findOneByName(@RequestParam("username") String username){
      boolean ifExist =  this.userService.checkIfUserExist(username);
      return ResponseEntity.ok(ifExist);
    }

    private HttpHeaders createAuthorizationHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        return headers;
    }

}
