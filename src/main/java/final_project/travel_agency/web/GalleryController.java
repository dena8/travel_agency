package final_project.travel_agency.web;

import final_project.travel_agency.model.binding.GalleryBindingModel;
import final_project.travel_agency.model.entity.Gallery;
import final_project.travel_agency.model.view.GalleryViewModel;
import final_project.travel_agency.repository.GalleryRepository;
import final_project.travel_agency.service.GalleryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("galleries")
public class GalleryController {
    private final GalleryService galleryService;
    private final GalleryRepository galleryRepository;
    private final ModelMapper modelMapper;

    public GalleryController(GalleryService galleryService, GalleryRepository galleryRepository, ModelMapper modelMapper) {
        this.galleryService = galleryService;
        this.galleryRepository = galleryRepository;
        this.modelMapper = modelMapper;
    }


    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> createGallery(@ModelAttribute("galleryBindingModel") GalleryBindingModel galleryBindingModel) {
        this.galleryService.createGallery(galleryBindingModel);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/view")
    public ResponseEntity<GalleryViewModel> getGalleryInformation() {
        List<Gallery> galleryList = this.galleryRepository.findAll();
        List<GalleryViewModel> galleries = galleryList.stream().map(g -> this.modelMapper.map(g, GalleryViewModel.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(galleries.get(0));
    }

}
