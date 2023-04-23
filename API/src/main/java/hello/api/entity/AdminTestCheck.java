package hello.api.entity;

import hello.api.enumforentity.Check;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AdminTestCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //다대일 양방향  매핑
    @ManyToOne
    @JoinColumn(name = "admin_sentence_id")
    private AdminSentence adminSentence;
    //다대일 단방향 매핑
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @Enumerated(EnumType.STRING)
    private Check testCheck;
    @CreatedDate
    private Timestamp createdDate;
    @LastModifiedDate
    private Timestamp updatedDate;

    public void ChangeTestCheck(Check check) {
        this.testCheck = check;
    }

}
