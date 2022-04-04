package byuntil.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Field {
    @Id @GeneratedValue
    @Column(name = "FIELD_ID")
    private Long id;

    private String name;

    private String description;
}
