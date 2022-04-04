package byuntil.backend.entity.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Professor extends Member{
    private String doctorate;
    private String location;
    private String number;
}
