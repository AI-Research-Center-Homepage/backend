package byuntil.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;


@Entity
public class Thesis {
    @Id
    @GeneratedValue
    @Column(name = "THESIS_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "FIELD_ID")
    private Field field;

    private String title;

    private String koName;
    private String enName;
    private String journal;
    private LocalDateTime publishDate;
    private String url;

    @OneToMany(mappedBy = "thesis")
    private List<Member_Thesis> members = new ArrayList<>();
}
