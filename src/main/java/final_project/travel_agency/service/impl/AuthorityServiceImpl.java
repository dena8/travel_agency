package final_project.travel_agency.service.impl;

import final_project.travel_agency.repository.AuthorityRepository;
import final_project.travel_agency.service.AuthorityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }


    @Override
    public List<String> getAuthorityNames() {
        return this.authorityRepository.getAuthorityNames();
    }
}
