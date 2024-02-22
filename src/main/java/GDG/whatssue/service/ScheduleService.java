package GDG.whatssue.service;

import GDG.whatssue.dto.schedule.request.AddScheduleRequest;
import GDG.whatssue.dto.schedule.reponse.GetScheduleResponse;
import GDG.whatssue.dto.schedule.request.ModifyScheduleRequest;
import GDG.whatssue.entity.Club;
import GDG.whatssue.entity.Schedule;
import GDG.whatssue.repository.ClubRepository;
import GDG.whatssue.repository.ScheduleRepository;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * ScheduleInterceptor에서 존재하는 schedule인지, club에 유효한 schedule인지 체크.
 * ScheduleInterceptor의 예외 처리 완료되면
 * ScheduleService의 유효한 스케줄인지 체크 로직 제거 TODO
 */

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ClubRepository clubRepository;

    public void saveSchedule(Long clubId, AddScheduleRequest requestDto) {
        Club club = clubRepository.findById(clubId).orElseThrow(
            () -> new NoSuchElementException());

        Schedule saveSchedule = requestDto.toEntity(club);
        scheduleRepository.save(saveSchedule);
    }

    @Transactional
    public void updateSchedule(Long scheduleId, ModifyScheduleRequest requestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
            () -> new NoSuchElementException());

        schedule.update(
            requestDto.getScheduleName(),
            requestDto.getScheduleContent(),
            requestDto.getScheduleDateTime());

        scheduleRepository.save(schedule);
    }

    /**
     * now HardDelete
     * SoftDelete TODO
     */
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    public GetScheduleResponse findSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
            () -> new NoSuchElementException());


        GetScheduleResponse scheduleDetailDto = GetScheduleResponse.builder()
            .scheduleId(schedule.getId())
            .scheduleName(schedule.getScheduleName())
            .scheduleContent(schedule.getScheduleContent())
            .scheduleDateTime(schedule.getScheduleDateTime().toString()).build();

        return scheduleDetailDto;
    }

    public List<GetScheduleResponse> findScheduleAllByFilter(Long clubId, String date, String month) {
        Club findClub = clubRepository.findById(clubId).get();

        List<Schedule> scheduleList = findClub.getScheduleList();
        List<GetScheduleResponse> responseDtoList;

        if(date==null && month==null ) { //전체조회
            responseDtoList = ScheduleListToResponseDtoList(scheduleList, null, null);
        } else if (date != null) { //일자별 조회
            responseDtoList = ScheduleListToResponseDtoList(scheduleList, "yyyy-MM-dd", date);
        } else { //월별 조회
            responseDtoList = ScheduleListToResponseDtoList(scheduleList, "yyyy-MM", month);
        }

        return responseDtoList;
    }

    public List<GetScheduleResponse> ScheduleListToResponseDtoList(List<Schedule> scheduleList, String pattern, String filter) {

        if (pattern==null || filter==null) { //전체 조회
            return scheduleList.stream()
                .map(s -> scheduleToGetScheduleResponse(s))
                .collect(Collectors.toList());
        } else { //필터링 조회
        return scheduleList.stream()
            .filter(s-> s.getScheduleDateTime().format(DateTimeFormatter.ofPattern(pattern)).equals(filter))
            .map(s -> scheduleToGetScheduleResponse(s))
            .collect(Collectors.toList());
        }
    }

    public GetScheduleResponse scheduleToGetScheduleResponse(Schedule schedule) {
        return GetScheduleResponse.builder()
            .scheduleId(schedule.getId())
            .scheduleName(schedule.getScheduleName())
            .scheduleContent(schedule.getScheduleContent())
            .scheduleDateTime(schedule.getScheduleDateTime().toString()).build();
    }
}

