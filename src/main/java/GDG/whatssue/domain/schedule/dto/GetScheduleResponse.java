package GDG.whatssue.domain.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetScheduleResponse {

    private Long scheduleId;
    private String scheduleName;
    private String scheduleContent;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private LocalDateTime scheduleDateTime;
    private boolean isChecked;
}
