package GDG.whatssue.controller;

import GDG.whatssue.dto.schedule.request.AddScheduleRequest;
import GDG.whatssue.dto.schedule.reponse.GetScheduleResponse;
import GDG.whatssue.dto.schedule.request.ModifyScheduleRequest;
import GDG.whatssue.service.ScheduleService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{clubId}/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * Filter 또는 Interceptor를 통해 schedule이 club의 것인지 체크 필요 TODO
     */

    @PostMapping
    public ResponseEntity addSchedule(@PathVariable(name = "clubId") Long clubId,
        @Valid @RequestBody AddScheduleRequest requestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            //bean validation 처리 TODO
        }

        try{
            scheduleService.saveSchedule(clubId, requestDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Club Id");
        }

        return ResponseEntity.status(200).body("ok");

    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity modifySchedule(@PathVariable(name = "clubId") Long clubId, @PathVariable(name = "scheduleId") Long scheduleId,
        @Valid @RequestBody ModifyScheduleRequest requestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            //bean validation 처리 TODO
        }

        try{
            scheduleService.updateSchedule(scheduleId, requestDto);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Schedule Id");
        }

        return ResponseEntity.status(HttpStatus.OK).body("ok");

    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity deleteSchedule(@PathVariable(name = "clubId") Long clubId, @PathVariable(name = "scheduleId") Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);

        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity getSchedule (@PathVariable(name = "clubId") Long clubId, @PathVariable(name = "scheduleId") Long scheduleId) {
        GetScheduleResponse scheduleDto;
        try{
            scheduleDto = scheduleService.findSchedule(scheduleId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Schedule Id");
        }

        return ResponseEntity.status(HttpStatus.OK).body(scheduleDto);
    }

    //전체조회
    @GetMapping
    public ResponseEntity getScheduleAll( @PathVariable(name = "clubId") Long clubId, @RequestParam(name = "date", required = false) String date,
        @RequestParam(name = "month", required = false) String month) {
        List<GetScheduleResponse> responseDtoList = scheduleService.findScheduleAllByFilter(clubId, date, month);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }


}