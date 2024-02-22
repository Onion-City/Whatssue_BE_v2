package GDG.whatssue.dto.Attendance;

import GDG.whatssue.entity.AttendanceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ScheduleAttendanceMemberDto {
    private Long clubId;
    private Long scheduleId;
    private Long clubMemberId;
    private AttendanceType attendanceType;
}
