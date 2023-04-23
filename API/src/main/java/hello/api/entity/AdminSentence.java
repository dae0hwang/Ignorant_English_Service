package hello.api.entity;

import hello.api.enumforentity.Grammar;
import hello.api.enumforentity.Situation;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"id", "korean"})
public class AdminSentence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String korean;
    private String english;
    @Enumerated(EnumType.STRING)
    private Grammar grammar;
    @Enumerated(EnumType.STRING)
    private Situation situation;
    @CreatedDate
    private Timestamp createdDate;
    @LastModifiedDate
    private Timestamp updatedDate;
    // 다대일 양방향 매핑(참조만하는 필드)
    @OneToMany(mappedBy = "adminSentence")
    List<AdminTestCheck> adminTestChecks = new ArrayList<>();


    public AdminSentence(String korean, String english, Grammar grammar,
        Situation situation) {
        this.korean = korean;
        this.english = english;
        this.grammar = grammar;
        this.situation = situation;
    }
}
