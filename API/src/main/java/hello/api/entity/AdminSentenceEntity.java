package hello.api.entity;

import hello.api.enumforentity.Grammar;
import hello.api.enumforentity.Situation;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Table(name = "admin_sentence")
public class AdminSentenceEntity {

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


    public AdminSentenceEntity(String korean, String english, Grammar grammar,
        Situation situation) {
        this.korean = korean;
        this.english = english;
        this.grammar = grammar;
        this.situation = situation;
    }
}
