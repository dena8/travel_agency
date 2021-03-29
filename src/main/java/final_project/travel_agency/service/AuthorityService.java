package final_project.travel_agency.service;

import final_project.travel_agency.model.service.AuthorityServiceModel;

import java.util.List;

public interface AuthorityService {
    List<String> getAuthorityNames();

    AuthorityServiceModel getAuthorityBuName(String authority);
}
