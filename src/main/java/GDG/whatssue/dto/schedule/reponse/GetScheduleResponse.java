package GDG.whatssue.dto.schedule.reponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetScheduleResponse {

    private Long scheduleId;
    private String scheduleName;
    private String scheduleContent;
    private String scheduleDateTime;
}
