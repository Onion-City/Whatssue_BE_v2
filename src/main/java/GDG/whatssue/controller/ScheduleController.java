package GDG.whatssue.controller;

import GDG.whatssue.dto.schedule.AddScheduleRequestDto;
import GDG.whatssue.dto.schedule.GetScheduleResponseDto;
import GDG.whatssue.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{clubId}/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity addSchedule(@PathVariable Long clubId, @RequestBody AddScheduleRequestDto requestDto) {

        scheduleService.createSchedule(clubId, requestDto);
        return ResponseEntity.status(200).body("ok");
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity getSchedule (@PathVariable(name = "clubId") Long clubId, @PathVariable(name = "scheduleId") Long scheduleId) {
        GetScheduleResponseDto scheduleDto = scheduleService.findSchedule(scheduleId);

        if (scheduleDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(scheduleDto);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Schedule Id");
    }
}

