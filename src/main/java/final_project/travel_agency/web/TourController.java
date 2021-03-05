package final_project.travel_agency.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import final_project.travel_agency.model.binding.TourBindingModel;
import final_project.travel_agency.model.dto.WeatherDtoModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.view.TourViewModel;
import final_project.travel_agency.service.TourService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;


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



    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createTour(@Valid @ModelAttribute("tour") TourBindingModel tour) throws NotFoundException, IOException, ParseException {

        this.tourService.createTour(tour);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


   // @PreAuthorize("hasAuthority('GUIDE_ROLE')")
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

    @GetMapping("/remove/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable String id) {
        this.tourService.deleteTour(id);
        return null;
    }

    @GetMapping("weather-forecast")
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
