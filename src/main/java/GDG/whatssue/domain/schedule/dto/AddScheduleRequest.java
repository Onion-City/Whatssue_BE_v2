package GDG.whatssue.domain.schedule.dto;

import GDG.whatssue.domain.member.entity.ClubMember;
import GDG.whatssue.domain.schedule.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class AddScheduleRequest {

    @NotBlank(message = "일정명은 필수 입력값입니다.")
    @Size(min = 2, max = 30, message = "일정명은 최소 2글자, 최대 30글자까지입니다.")
    private String scheduleName;
    @NotNull
    @Size(max = 1000, message = "일정 내용은 최대 1000글자까지입니다.")
    private String scheduleContent;
    @NotNull
    @Size(max = 30, message = "일정 장소는 최대 30글자까지입니다.")
    private String schedulePlace;

    @NotBlank(message = "일정 날짜는 필수입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;

    @NotBlank(message = "일정 시간은 필수입니다.")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime scheduleTime;


    public Schedule toEntity(ClubMember register) {
        return Schedule.createSchedule(
            register, scheduleName,
            scheduleContent, scheduleDate, scheduleTime, schedulePlace);
    }
}
