package hello.plusapi.entity;

import hello.plusapi.enumforentity.AlarmType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Alarm {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sentence_group_id")
    private SentenceGroup sentenceGroup;
    @ManyToOne
    @JoinColumn(name = "subscribed_user_id")
    private Users subscribedUser;
    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;
    private String alarmMessage;
}
