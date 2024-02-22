package GDG.whatssue.dto.schedule.request;

import GDG.whatssue.entity.Club;
import GDG.whatssue.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AddScheduleRequest {

    @NotBlank
    private String scheduleName;
    @NotBlank
    private String scheduleContent;

    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime scheduleDateTime;

    public Schedule toEntity(Club club) {
        return Schedule.builder()
            .club(club)
            .scheduleName(scheduleName)
            .scheduleContent(scheduleContent)
            .scheduleDateTime(scheduleDateTime)
            .isChecked(false).build();
    }
}
