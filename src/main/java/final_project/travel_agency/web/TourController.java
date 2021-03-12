package final_project.travel_agency.web;

import com.google.gson.Gson;
import final_project.travel_agency.exception.NotCorrectDataEx;
import final_project.travel_agency.model.binding.TourBindingModel;
import final_project.travel_agency.model.dto.WeatherDtoModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.service.UserServiceModel;
import final_project.travel_agency.model.view.TourViewModel;
import final_project.travel_agency.service.TourService;
import final_project.travel_agency.service.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/tours")
public class TourController {

    private final TourService tourService;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final Gson gson;



    public TourController(TourService tourService, ModelMapper modelMapper, RestTemplate restTemplate, Gson gson) {
        this.tourService = tourService;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
        this.gson = gson;
    }



    @PreAuthorize("hasAuthority('GUIDE_ROLE')")
    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createTour(@Valid @ModelAttribute TourBindingModel tour, BindingResult bindingResult) throws NotCorrectDataEx, NotFoundException, IOException {
        if(bindingResult.hasErrors()){
            List<String> validationList = bindingResult.getFieldErrors().stream().map(b->b.getDefaultMessage()).collect(Collectors.toList());
            throw new NotCorrectDataEx("Provided data is not correct!",validationList);
        }
        this.tourService.createTour(tour);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<TourViewModel[]> getAllTours() {
        TourServiceModel[] tourServiceModels = this.tourService.getAllTours();
        return new ResponseEntity<>(this.modelMapper.map(tourServiceModels, TourViewModel[].class), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourViewModel> getTour(@PathVariable String id) throws NotFoundException {
        TourServiceModel tourServiceModel = this.tourService.getTourById(id);
        return new ResponseEntity<>(this.modelMapper.map(tourServiceModel, TourViewModel.class), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    @GetMapping("/remove/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable String id) throws NotFoundException {
        this.tourService.deleteTour(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("weather-forecasttt")
    public ResponseEntity<WeatherDtoModel> getWeather(@RequestParam String region)  {
        String weatherAsString = restTemplate.getForObject("http://api.weatherstack.com/current?access_key=8dfb900cd87a27bee8fff4d7f0425a55&query="+region, String.class);
        WeatherDtoModel weather = this.gson.fromJson(weatherAsString,WeatherDtoModel.class);
        if(weatherAsString.contains("error")){
            return new ResponseEntity<>(weather,HttpStatus.NOT_FOUND);
        }
        weather.setSuccess(true);
        return new ResponseEntity<>(weather,HttpStatus.OK);
    }

}
