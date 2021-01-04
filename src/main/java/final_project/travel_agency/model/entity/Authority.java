package final_project.travel_agency.model.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
public class Authority extends BaseEntity implements GrantedAuthority {

    private String authority;


    public Authority(String authority) {
        this.authority=authority;
    }

    public Authority() {
    }

    @Override
    @Column(name = "authority", unique = false)
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
