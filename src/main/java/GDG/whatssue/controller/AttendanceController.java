package GDG.whatssue.controller;

import GDG.whatssue.dto.Attendance.AttendanceNumResponseDto;
import GDG.whatssue.dto.Attendance.ScheduleAttendanceMemberDto;
import GDG.whatssue.dto.Attendance.ScheduleAttendanceRequestDto;
import GDG.whatssue.entity.ScheduleAttendanceResult;
import GDG.whatssue.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RequestMapping("/api/{clubId}/schedules/{scheduleId}")
@RestController
@Slf4j
public class AttendanceController {
    private final AttendanceService attendanceService;

    @Operation(summary = "출석 열기_ADMIN")
    @GetMapping("/attendance-start")
    @PreAuthorize("hasRole('ROLE_'+#clubId+'MANAGER')")
    public ResponseEntity openAttendance(@PathVariable Long clubId, @PathVariable Long scheduleId) {
        AttendanceNumResponseDto dto;
        try {
            dto = attendanceService.openAttendance(clubId, scheduleId);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @Operation(summary = "출석 종료")
    @PostMapping("/attendance-end")
    public ResponseEntity offAttendance(@PathVariable Long clubId, @PathVariable Long scheduleId) {
        try{
            attendanceService.deleteAttendance(clubId, scheduleId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }
    @Operation(summary = "출석한 멤버 리스트 조회")
    @GetMapping("/attendance-list")
    public ResponseEntity getAttendanceList( @PathVariable Long clubId, @PathVariable Long scheduleId) throws Exception {
        List<ScheduleAttendanceMemberDto> list =  attendanceService.getAttendanceList(scheduleId, clubId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @Operation(summary = "출석하기 _ user")
    @PostMapping("/attendance")
    public ResponseEntity doAttendance(@PathVariable Long clubId, @PathVariable Long scheduleId, @RequestBody ScheduleAttendanceRequestDto requestDto) {
        try {
            attendanceService.doAttendance(clubId, scheduleId, requestDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("출석이 완료되었습니다.");
    }
}



