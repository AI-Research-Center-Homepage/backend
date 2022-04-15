package byuntil.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Admin {
    @Id@GeneratedValue
    @Column(name = "ADMIN_ID")
    private Long id;

    private String loginId;

    private String loginPw;
}
