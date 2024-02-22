package GDG.whatssue.dto.Attendance;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleAttendanceRequestDto {
    private Long clubMemberId;
    private int attendanceNum;

}
