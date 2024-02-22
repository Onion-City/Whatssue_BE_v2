package GDG.whatssue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;

@Getter
@Entity
@Builder
@AllArgsConstructor
public class OfficialAbsenceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "official_absence_request_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "club_member_id", nullable = false)
    private ClubMember clubMember;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @JoinColumn(name = "official_absence_content", nullable = false)
    private String officialAbsenceContent;
    public OfficialAbsenceRequest() {
        // 기본 생성자
    }
}
