package final_project.travel_agency;

import final_project.travel_agency.exception.NotCorrectDataEx;
import final_project.travel_agency.exception.NotFoundEx;
import final_project.travel_agency.model.binding.UserBindingModel;
import final_project.travel_agency.model.entity.Authority;
import final_project.travel_agency.model.entity.Tour;
import final_project.travel_agency.model.entity.User;
import final_project.travel_agency.model.service.AuthorityServiceModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.repository.UserRepository;
import final_project.travel_agency.service.AuthorityService;
import final_project.travel_agency.service.TourService;
import final_project.travel_agency.service.UserService;
import final_project.travel_agency.service.impl.UserServiceImpl;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository mockRepository;
    @Mock
    private TourService mockTourService;
    @Mock
    private AuthorityService mockAuthorityService;

    private User user1;
    private AuthorityServiceModel authorityServiceModel;


    private UserService serviceToTest;

    @BeforeEach
    private void Init() {
        this.authorityServiceModel = new AuthorityServiceModel();
        this.authorityServiceModel.setAuthority("USER_ROLE");
        Authority authority = new Authority();
        authority.setAuthority("USER_ROLE");
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        this.user1 = new User();
        user1.setId("user1");
        user1.setUsername("user1");
        user1.setAuthorities(authorities);
        Tour tour = new Tour();
        tour.setId("t2");
        tour.setParticipants(3);
        user1.getCart().add(tour);

        this.serviceToTest = new UserServiceImpl(new ModelMapper(), mockRepository, mockTourService, mockAuthorityService, new BCryptPasswordEncoder());
    }

    @Test
    public void getUserById_Should_Return_UserServiceModel() {
        when(this.mockRepository.findById("user1")).thenReturn(Optional.of(user1));
        UserServiceModel result = this.serviceToTest.getUserById("user1");
        Assert.assertNotNull(result);
    }

    @Test
    public void removeItemFromCart_should_remove() throws NotFoundException {
        Tour tour = new Tour();
        tour.setId("t1");
        this.user1.getCart().add(tour);
        TourServiceModel tourServiceModel = new TourServiceModel();
        tourServiceModel.setId("t1");
        when(this.mockRepository.findById("user1")).thenReturn(Optional.of(user1));
        when(this.mockTourService.getTourById("t1")).thenReturn(tourServiceModel);
        this.serviceToTest.removeItemFromCart("user1", "t1");
        verify(this.mockRepository).saveAndFlush(any());
    }

    @Test
    public void removeItemFromCart_should_throw_ex() throws NotFoundException {
        when(this.mockRepository.findById("userId")).thenThrow(new NotFoundEx("User not found"));

        NotFoundEx ex = Assertions.assertThrows(NotFoundEx.class, () -> {
            this.serviceToTest.removeItemFromCart("userId", "tourTd");
        }, "removeItemFromCart should throw ex, but not");

        Assertions.assertTrue(ex.getMessage().contains("User not found"));
    }

    @Test
    public void getAuthorityNames_should_return_names() {
        when(this.mockAuthorityService.getAuthorityNames()).thenReturn(List.of("USER_ROLE", "ADMIN_ROLE"));
        List<String> result = this.serviceToTest.getAuthorityNames();
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("ADMIN_ROLE", result.get(1));
    }

    @Test
    public void updateAuthority_should_return_right_result() {
        UserBindingModel userBindingModel = new UserBindingModel();
        userBindingModel.setUsername("user1");
        userBindingModel.setAuthority("GUIDE_ROLE");
        this.authorityServiceModel.setAuthority(userBindingModel.getAuthority());
        when(this.mockRepository.findByUsername(userBindingModel.getUsername())).thenReturn(Optional.of(user1));
        when(this.mockAuthorityService.getAuthorityBuName(userBindingModel.getAuthority())).thenReturn(authorityServiceModel);//
        when(this.mockRepository.saveAndFlush(user1)).thenReturn(user1);
        UserServiceModel result = this.serviceToTest.updateAuthority(userBindingModel);
        Assertions.assertEquals("GUIDE_ROLE", result.getAuthorities().get(0).getAuthority());
    }

    @Test
    public void updateAuthority_should_return_NotFoundEx() {
        UserBindingModel userBindingModel = new UserBindingModel();
        userBindingModel.setUsername("noSuchId");
        userBindingModel.setAuthority("GUIDE_ROLE");
        when(this.mockRepository.findByUsername(userBindingModel.getUsername())).thenThrow(new NotFoundEx("user not found"));

        NotFoundEx ex = Assertions.assertThrows(NotFoundEx.class, () -> {
            this.serviceToTest.updateAuthority(userBindingModel);
        });
        Assertions.assertTrue(ex.getMessage().contains("user not found"));
    }

    @Test
    public void registerTest() {
        when(this.mockAuthorityService.getAuthorityBuName("ADMIN_ROLE")).thenReturn(authorityServiceModel);
        UserServiceModel user1ServiceModel = getUserServiceModelUser1();
        user1ServiceModel.setPassword("password");
        this.serviceToTest.register(user1ServiceModel);
        Mockito.verify(this.mockRepository, times(1)).saveAndFlush(any(User.class));
    }

    @Test
    public void loadUserByUsername_should_return_UserDetails() {
        when(this.mockRepository.findByUsername("user1")).thenReturn(Optional.of(user1));
        UserDetails result = this.serviceToTest.loadUserByUsername("user1");
        Assertions.assertEquals("user1", result.getUsername());
    }

    @Test
    public void loadUserByUsername_should_throw_NotFoundEx() {
        when(this.mockRepository.findByUsername("user1")).thenThrow(new NotFoundEx("No such user exist"));

        NotFoundEx ex = Assertions.assertThrows(NotFoundEx.class, () -> {
            this.serviceToTest.loadUserByUsername("user1");
        }, "loadUserByUsername should throw ex, but not");

        Assertions.assertTrue(ex.getMessage().contains("No such user exist"));
    }

    @Test
    public void emptiedCard_should_work_correctly() throws NotFoundException {
        UserServiceModel user1ServiceModel = getUserServiceModelUser1();
        when(this.mockRepository.findByUsername("user1")).thenReturn(Optional.of(user1));
        this.serviceToTest.emptiedCard(user1ServiceModel);
        Mockito.verify(this.mockRepository, times(1)).saveAndFlush(any(User.class));

    }

    @Test
    public void emptiedCard_should_throw_ex() throws NotFoundException {
        UserServiceModel user1ServiceModel = getUserServiceModelUser1();
        when(this.mockRepository.findByUsername("user1")).thenThrow(new NotFoundEx("User not found"));

        NotFoundEx ex = Assertions.assertThrows(NotFoundEx.class, () -> {
            this.serviceToTest.emptiedCard(user1ServiceModel);
        }, "emptiedCard should throw ex, but not");

        Assertions.assertTrue(ex.getMessage().contains("User not found"));

    }

    @Test
    public void getAuthenticatedUser_should_return_authenticated_user() {
        Authentication auth = getAuthentication();
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(this.mockRepository.findByUsername("user1")).thenReturn(Optional.of(user1));
        UserServiceModel result = this.serviceToTest.getAuthenticatedUser();
        Assertions.assertEquals("user1", result.getUsername(), "Method should return correct result, but not");
    }

    @Test
    public void getAuthenticatedUser_should_return_NotFoundEx() {
        Authentication auth = getAuthentication();
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(this.mockRepository.findByUsername("user1")).thenThrow(new NotFoundEx("No such user exist"));

        NotFoundEx ex = Assertions.assertThrows(NotFoundEx.class, () -> {
            this.serviceToTest.getAuthenticatedUser();
        }, "getAuthenticatedUser should throw ex, but not");

        Assertions.assertTrue(ex.getMessage().contains("No such user exist"));

    }

    @Test
    public void addTourToCart_should_work_correctly() throws Exception {
        Authentication auth = getAuthentication();
        TourServiceModel tour = getTourServiceModel();
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);
        when(this.mockRepository.findByUsername("user1")).thenReturn(Optional.of(user1));
        when(this.mockTourService.getTourById("t3")).thenReturn(tour);
        this.serviceToTest.addTourToCart("t3");
        Mockito.verify(this.mockRepository, times(1)).saveAndFlush(any(User.class));
    }

    @Test
    public void addTourToCart_should_throw_NotCorrectDataEx() throws Exception {
        TourServiceModel tour = getTourServiceModel();
        tour.setParticipants(0);
        when(this.mockTourService.getTourById("t3")).thenReturn(tour);

        NotCorrectDataEx ex = Assertions.assertThrows(NotCorrectDataEx.class, () -> {
            this.serviceToTest.addTourToCart("t3");
        }, "addTourToCart should throw ex, but not");

        Assertions.assertTrue(ex.getMessage().contains("No vacant places"));
    }

    @Test
    public void checkIfUserExist_should_return_true() {
        String username = "user1";
        when(this.mockRepository.findByUsername(username)).thenReturn(Optional.of(user1));
        boolean result = this.serviceToTest.checkIfUserExist(username);
        Assertions.assertTrue(result, "checkIfUserExist must return true, but return false");
    }

    @Test
    public void checkIfUserExist_should_return_false() {
        String username = "noUser";
        when(this.mockRepository.findByUsername(username)).thenReturn(Optional.empty());
        boolean result = this.serviceToTest.checkIfUserExist(username);
        Assertions.assertFalse(result, "checkIfUserExist must return false, but return true");
    }


    private UserServiceModel getUserServiceModelUser1() {
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("user1");
        return userServiceModel;
    }

    private Authentication getAuthentication() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "user1";
            }
        };
    }

    private TourServiceModel getTourServiceModel() {
        TourServiceModel tourServiceModel = new TourServiceModel();
        tourServiceModel.setId("t3");
        tourServiceModel.setParticipants(3);
        tourServiceModel.setName("t3");
        return tourServiceModel;
    }


}
