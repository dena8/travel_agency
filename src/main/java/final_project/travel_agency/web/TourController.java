package final_project.travel_agency.web;

import final_project.travel_agency.model.binding.TourBindingModel;
import final_project.travel_agency.model.service.TourServiceModel;
import final_project.travel_agency.model.view.TourViewModel;
import final_project.travel_agency.service.TourService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;


@RestController
@RequestMapping("/tours")
public class TourController {

    private final TourService tourService;
    private final ModelMapper modelMapper;


    public TourController(TourService tourService, ModelMapper modelMapper) {
        this.tourService = tourService;
        this.modelMapper = modelMapper;
    }


    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createTour(@Valid @ModelAttribute("tour") TourBindingModel tour) throws NotFoundException, IOException, ParseException {
        System.out.println();
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

    @GetMapping("/remove/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable String id) {
        this.tourService.deleteTour(id);
        return null;
    }

}
