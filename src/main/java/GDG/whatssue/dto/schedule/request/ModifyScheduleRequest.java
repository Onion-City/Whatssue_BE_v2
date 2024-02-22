package GDG.whatssue.dto.schedule.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyScheduleRequest {

    @NotBlank
    private String scheduleName;

    @NotBlank
    private String scheduleContent;

    @NotBlank
    private LocalDateTime scheduleDateTime;
}
