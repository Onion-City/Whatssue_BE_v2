package GDG.whatssue.repository;

import GDG.whatssue.entity.Schedule;
import GDG.whatssue.entity.ScheduleAttendanceResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
