package final_project.travel_agency.service.impl;

import final_project.travel_agency.exception.NotFoundEx;
import final_project.travel_agency.model.entity.Authority;
import final_project.travel_agency.model.service.AuthorityServiceModel;
import final_project.travel_agency.repository.AuthorityRepository;
import final_project.travel_agency.service.AuthorityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;
    private final ModelMapper modelMapper;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository, ModelMapper modelMapper) {
        this.authorityRepository = authorityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<String> getAuthorityNames() {
        return this.authorityRepository.getAuthorityNames();
    }

    @Override
    public AuthorityServiceModel getAuthorityBuName(String authority) {
        Authority authorityEntity = this.authorityRepository.findByAuthority(authority).orElseThrow(()->new NotFoundEx("Authority not found"));
        return this.modelMapper.map(authorityEntity,AuthorityServiceModel.class);
    }
}
