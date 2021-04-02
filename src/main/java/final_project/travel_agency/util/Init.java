package final_project.travel_agency.util;

import final_project.travel_agency.model.entity.Authority;
import final_project.travel_agency.model.entity.Category;
import final_project.travel_agency.model.entity.Gallery;
import final_project.travel_agency.repository.AuthorityRepository;
import final_project.travel_agency.repository.CategoryRepository;
import final_project.travel_agency.repository.GalleryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Init implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final CategoryRepository categoryRepository;
    private final GalleryRepository galleryRepository;


    public Init(AuthorityRepository authorityRepository, CategoryRepository categoryRepository, GalleryRepository galleryRepository) {
        this.authorityRepository = authorityRepository;
        this.categoryRepository = categoryRepository;
        this.galleryRepository = galleryRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        if(this.authorityRepository.count()<1){
            Authority admin = new Authority("ADMIN_ROLE");
            Authority user = new Authority("USER_ROLE");
            Authority guide = new Authority("GUIDE_ROLE");
            this.authorityRepository.saveAndFlush(admin);
            this.authorityRepository.saveAndFlush(user);
            this.authorityRepository.saveAndFlush(guide);
        }

        if(this.categoryRepository.count()<1){
            this.categoryRepository.saveAndFlush(new Category("Alpinism"));
            this.categoryRepository.saveAndFlush(new Category("Trek"));
        }

        if(this.galleryRepository.count()<1){
            Gallery gallery = new Gallery();
            gallery.setDescription("There are a million ways to travel, but few resonate in the way that trekking does. Get back to nature, challenge yourself and enter landscapes well beyond our urban environments. Picture yourself enjoying a spontaneous moment as you smile back at a nomadic mountain lady, snack by a rushing stream, follow winding paths that weave through the mountains, and scale rugged and rocky terrain in search of the perfect sunrise.");
            gallery.setImages(List.of(
                    "https://scontent-otp1-1.xx.fbcdn.net/v/t1.18169-9/529919_3599796127947_1669794940_n.jpg?_nc_cat=111&ccb=1-3&_nc_sid=cdbe9c&_nc_ohc=tct0L1Gr0ygAX9G--JT&_nc_ht=scontent-otp1-1.xx&oh=ae0752d3d173f2db7adc6b6e0e5a98f1&oe=608DBF99",
                    "https://scontent-otp1-1.xx.fbcdn.net/v/t1.18169-9/535666_3599817088471_1702284168_n.jpg?_nc_cat=109&ccb=1-3&_nc_sid=cdbe9c&_nc_ohc=JP0jkC1EkO4AX8Rth8W&_nc_ht=scontent-otp1-1.xx&oh=e176736d46f2cb1dcc142fe13dca7411&oe=608B1F20",
                    "https://scontent-otp1-1.xx.fbcdn.net/v/t1.18169-9/156236_3600134056395_866807922_n.jpg?_nc_cat=108&ccb=1-3&_nc_sid=cdbe9c&_nc_ohc=4P71EW5VsoQAX9GR4Hb&_nc_ht=scontent-otp1-1.xx&oh=1fd815e574b1796cf569e34914be3ff1&oe=608C53F9",
                    "https://scontent-otp1-1.xx.fbcdn.net/v/t1.18169-9/480224_3600114215899_1369998591_n.jpg?_nc_cat=106&ccb=1-3&_nc_sid=cdbe9c&_nc_ohc=Np4Vru6l0DoAX_Gj8Od&_nc_ht=scontent-otp1-1.xx&oh=b4f36b6e1b263b83d47f99503fe20113&oe=608C7EA5"

            ));
            this.galleryRepository.saveAndFlush(gallery);
        }
    }
}
