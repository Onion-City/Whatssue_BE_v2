package GDG.whatssue.domain.attendance.service;

import GDG.whatssue.domain.attendance.Error.AttendanceErrorCode;
import GDG.whatssue.domain.attendance.dto.AttendanceNumRequestDto;
import GDG.whatssue.domain.attendance.dto.AttendanceNumResponseDto;
import GDG.whatssue.domain.attendance.dto.ScheduleAttendanceMemberDto;
import GDG.whatssue.domain.attendance.dto.ScheduleDto;
import GDG.whatssue.domain.club.exception.ClubErrorCode;
import GDG.whatssue.domain.member.entity.ClubMember;
import GDG.whatssue.domain.officialabsence.entity.OfficialAbsenceRequest;
import GDG.whatssue.domain.officialabsence.entity.OfficialAbsenceRequestType;
import GDG.whatssue.domain.officialabsence.repository.OfficialAbsenceRequestRepository;
import GDG.whatssue.domain.schedule.entity.AttendanceStatus;
import GDG.whatssue.domain.schedule.entity.Schedule;
import GDG.whatssue.domain.attendance.entity.AttendanceType;
import GDG.whatssue.domain.attendance.entity.ScheduleAttendanceResult;
import GDG.whatssue.domain.member.repository.ClubMemberRepository;
import GDG.whatssue.domain.attendance.repository.ScheduleAttendanceResultRepository;
import GDG.whatssue.domain.schedule.repository.ScheduleRepository;

import GDG.whatssue.domain.schedule.service.ScheduleFacade;
import GDG.whatssue.global.error.CommonException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {
    private static Map<Long, Map<Long, Integer>> attendanceNumMap = new HashMap<>();

    private final ScheduleAttendanceResultRepository scheduleAttendanceResultRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleFacade scheduleFacade;
    private final AttendanceFacade attendanceFacade;
    private final OfficialAbsenceRequestRepository officialAbsenceRequestRepository;
    public final static Random random = new Random();

    @Transactional
    public AttendanceNumResponseDto openAttendance(Long clubId, Long scheduleId) {

        Schedule schedule = scheduleFacade.getSchedule(clubId, scheduleId);
        // 출석 가능 여부 확인 및 예외 처리
        schedule.startAttendance();
        // 출석을 진행하기 전, 모든 멤버의 해당 일정의 출석 상태를 absence 으로 변경
        initializeMemberAttendance(clubId,scheduleId);

        // 출석번호 생성 및 맵에 저장
        int randomInt = putAttendanceNumInMapAndReturn(clubId, scheduleId);

        AttendanceNumResponseDto responseDto = AttendanceNumResponseDto.of(clubId, scheduleId, randomInt);

        return responseDto;
    }

    //현재 진행중인 일정 리스트
    public List<ScheduleDto> currentAttendanceList(Long clubId) {

        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        List<Schedule> scheduleList =  scheduleFacade.getSchedule(clubId);
        for(Schedule schedule : scheduleList){
            if(schedule.getAttendanceStatus() == AttendanceStatus.ONGOING){
                if (attendanceNumMap.get(clubId).containsKey(schedule.getId())){
                    scheduleDtoList.add(ScheduleDto.of(schedule));
                }else{
                    throw new CommonException(AttendanceErrorCode.EX5204);
                }
            }
        }
            return scheduleDtoList;
    }

    /*Delete 시에 결석자 명단을 업로드해야할까?*/
    @Transactional
    public void finishAttendanceOngoing(Long clubId, Long scheduleId) {

        Schedule schedule = scheduleFacade.getSchedule(clubId, scheduleId);
        // 조건 체크 및 스케줄 상태 변경
        schedule.finishAttendance();

        // Map에서 출석 번호 삭제
        if(attendanceNumMap.containsKey(clubId) && attendanceNumMap.get(clubId).containsKey(scheduleId))
            attendanceNumMap.get(clubId).remove(scheduleId);
        else throw new CommonException(AttendanceErrorCode.EX5203);

    }

    public List<ScheduleAttendanceMemberDto> getAttendanceList(Long scheduleId, Long clubId) throws Exception {
        List<ScheduleAttendanceResult> attendanceList;
        attendanceList = scheduleAttendanceResultRepository.findByScheduleId(scheduleId);

        if (attendanceList.isEmpty()) throw new Exception("출석한 멤버가 존재하지 않습니다.");
        List<ScheduleAttendanceMemberDto> attendedMembers = (List<ScheduleAttendanceMemberDto>) attendanceList.stream().map(m -> {
            if (m.getAttendanceType().toString().equals("ATTENDANCE")) {
                return ScheduleAttendanceMemberDto.builder()
                        .clubId(clubId)
                        .scheduleId(scheduleId)
                        .clubMemberId(m.getClubMember().getId())
                        .attendanceType(m.getAttendanceType())
                        .build();
            } else return null;
        });

        return attendedMembers;
    }

    @Transactional
    public void doAttendance(Long clubId, Long schduleId, Long memberId, AttendanceNumRequestDto requestDto) throws Exception{

        int attendanceNum = attendanceNumMap.get(clubId).get(schduleId);
        int inputValue = requestDto.getAttendanceNum();

        if (attendanceNum == inputValue) {
            ScheduleAttendanceResult scheduleAttendanceResult = attendanceFacade.getAttendanceResult(schduleId, memberId);
            scheduleAttendanceResult.setAttendanceType(AttendanceType.ATTENDANCE);
        } else throw new Exception(" 출석번호가 일치하지 않습니다. 다시 시도해 주세요");
    }



    public void modifyMemberAttendance(Long scheduleId, Long memberId, String attendanceType){

        ScheduleAttendanceResult attendanceResult = scheduleAttendanceResultRepository.findByScheduleIdAndClubMemberId(scheduleId, memberId)
                .orElseThrow(() -> new RuntimeException("해당 일정에 대한 출석 결과가 존재하지 않습니다."));
        AttendanceType type = AttendanceType.valueOf(attendanceType.toUpperCase());

        if(type.equals(AttendanceType.OFFICIAL_ABSENCE)) {
            OfficialAbsenceRequest officialAbsenceRequest = OfficialAbsenceRequest.builder()
                    .clubMember(attendanceResult.getClubMember())
                    .schedule(attendanceResult.getSchedule())
                    .officialAbsenceContent("관리자의 출석 정정에 의해 인정된 공결입니다.")
                    .officialAbsenceRequestType(OfficialAbsenceRequestType.ACCEPTED)
                    .build();
            officialAbsenceRequestRepository.save(officialAbsenceRequest);
        }
        attendanceResult.setAttendanceType(type);
        scheduleAttendanceResultRepository.save(attendanceResult);
    }

    private void initializeMemberAttendance(Long clubId, Long scheduleId) throws RuntimeException {

        List<ClubMember> clubMembers = clubMemberRepository.findByClubId(clubId).orElseThrow(()->new CommonException(ClubErrorCode.EX3100));

        for(ClubMember clubMember : clubMembers){
            ScheduleAttendanceResult scheduleAttendanceResult = ScheduleAttendanceResult.builder()
                    .clubMember(clubMember)
                    .schedule(scheduleFacade.getSchedule(clubId, scheduleId))
                    .attendanceType(AttendanceType.ABSENCE)
                    .build();
            scheduleAttendanceResultRepository.save(scheduleAttendanceResult);
        }
    }

    private int putAttendanceNumInMapAndReturn(Long clubId, Long scheduleId){

        AttendanceService.attendanceNumMap.put(clubId, new HashMap<>());

        int randomInt = AttendanceService.random.nextInt(1, 1000);

        Map<Long, Integer> innerMap = attendanceNumMap.get(clubId);
        innerMap.put(scheduleId, randomInt);
        randomInt = attendanceNumMap.get(clubId).get(scheduleId);

        return randomInt;
    }


}