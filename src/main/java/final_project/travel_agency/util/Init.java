package final_project.travel_agency.util;

import final_project.travel_agency.model.entity.Authority;
import final_project.travel_agency.repository.AuthorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    public Init(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
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
    }
}
