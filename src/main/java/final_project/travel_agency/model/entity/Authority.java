package final_project.travel_agency.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
public class Authority extends BaseEntity {

    private String authority;

    public Authority(String authority) {
        this.authority=authority;
    }

    public Authority() {
    }

    @Column
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
