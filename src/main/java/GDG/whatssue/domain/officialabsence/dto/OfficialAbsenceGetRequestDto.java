package GDG.whatssue.domain.officialabsence.dto;

import GDG.whatssue.domain.officialabsence.entity.OfficialAbsenceRequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class OfficialAbsenceGetRequestDto {

    private Long id;
    private Long clubMemberId;
    private String clubMemberName;
    private Long scheduleId;
    private Long officialAbsenceId;
    private String scheduleName;
    private LocalDateTime scheduleDate;
    private String officialAbsenceContent;
    private OfficialAbsenceRequestType officialAbsenceRequestType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}