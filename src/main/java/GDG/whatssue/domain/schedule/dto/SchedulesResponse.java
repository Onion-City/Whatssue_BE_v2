package GDG.whatssue.domain.schedule.dto;

import GDG.whatssue.domain.schedule.entity.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SchedulesResponse {

    private Long scheduleId;
    private String scheduleName;
    private AttendanceStatus attendanceStatus;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime scheduleTime;

    public SchedulesResponse(Long scheduleId, String scheduleName, AttendanceStatus attendanceStatus, LocalDateTime scheduleDate) {
        this.scheduleId = scheduleId;
        this.scheduleName = scheduleName;
        this.attendanceStatus = attendanceStatus;
        this.scheduleDate = scheduleDate.toLocalDate();
        this.scheduleTime = scheduleDate.toLocalTime();
    }
}
