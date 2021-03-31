package final_project.travel_agency.service.impl;

import final_project.travel_agency.model.binding.GalleryBindingModel;
import final_project.travel_agency.model.entity.Gallery;
import final_project.travel_agency.model.service.GalleryServiceModel;
import final_project.travel_agency.repository.GalleryRepository;
import final_project.travel_agency.service.CloudinaryService;
import final_project.travel_agency.service.GalleryService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GalleryServiceImpl implements GalleryService {
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final GalleryRepository galleryRepository;

    public GalleryServiceImpl(CloudinaryService cloudinaryService, ModelMapper modelMapper, GalleryRepository galleryRepository) {
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.galleryRepository = galleryRepository;
    }

    @Override
    public void createGallery(GalleryBindingModel galleryBindingModel) {
        this.galleryRepository.deleteAll();

     List<String> uplosdedImage=  galleryBindingModel.getImages().stream().map(i-> {
         try {
             return this.cloudinaryService.uploadImage(i);
         } catch (IOException e) {
             e.printStackTrace();
         }
         return null;
     }).collect(Collectors.toList());

        GalleryServiceModel galleryServiceModel = this.modelMapper.map(galleryBindingModel,GalleryServiceModel.class);
        galleryServiceModel.setImages(uplosdedImage);
        this.galleryRepository.saveAndFlush(this.modelMapper.map(galleryServiceModel, Gallery.class));

    }
}
