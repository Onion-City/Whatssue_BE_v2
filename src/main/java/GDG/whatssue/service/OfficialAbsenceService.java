package GDG.whatssue.service;

import GDG.whatssue.dto.OfficialAbsence.OfficialAbsenceAddRequestDto;
import GDG.whatssue.dto.OfficialAbsence.OfficialAbsenceGetRequestDto;
import GDG.whatssue.entity.ClubMember;
import GDG.whatssue.entity.OfficialAbsenceRequest;
import GDG.whatssue.entity.Schedule;
import GDG.whatssue.entity.ScheduleAttendanceResult;
import GDG.whatssue.repository.ClubMemberRepository;
import GDG.whatssue.repository.OfficialAbsenceRequestRepository;
import GDG.whatssue.repository.ScheduleAttendanceResultRepository;
import GDG.whatssue.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

import static GDG.whatssue.entity.AttendanceType.ABSENCE;
import static GDG.whatssue.entity.AttendanceType.OFFICIAL_ABSENCE;

@Service
@RequiredArgsConstructor
public class OfficialAbsenceService {
    private final ScheduleRepository scheduleRepository;
    private final OfficialAbsenceRequestRepository officialAbsenceRequestRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ScheduleAttendanceResultRepository scheduleAttendanceResultRepository;
    private static final Logger logger = LoggerFactory.getLogger(OfficialAbsenceService.class);

    public void createOfficialAbsenceRequest(Long scheduleId, OfficialAbsenceAddRequestDto officialAbsenceAddRequestDto) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);

        Schedule schedule = optionalSchedule.orElseThrow(() ->
                new NoSuchElementException("No schedule found for scheduleId: " + scheduleId));

        ClubMember clubMember = clubMemberRepository.findById(officialAbsenceAddRequestDto.getClubMemberId())
                .orElseThrow(() -> new NoSuchElementException("No club member found for memberId: " + officialAbsenceAddRequestDto.getClubMemberId()));
        String officialAbsenceContent = officialAbsenceAddRequestDto.getOfficialAbsenceContent();

        ClubMember clubMemberEntity = clubMemberRepository.findById(clubMember.getId()).get();

        OfficialAbsenceRequest officialAbsenceRequest = OfficialAbsenceRequest.builder()
                .clubMember(clubMemberEntity)
                .schedule(schedule)
                .officialAbsenceContent(officialAbsenceContent)
                .build();

        officialAbsenceRequestRepository.save(officialAbsenceRequest);

    }

    public List<OfficialAbsenceGetRequestDto> getOfficialAbsenceRequests() { //공결 신청 List 조회
        List<OfficialAbsenceRequest> officialAbsenceRequests = officialAbsenceRequestRepository.findAll();

        return officialAbsenceRequests.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OfficialAbsenceGetRequestDto convertToDto(OfficialAbsenceRequest officialAbsenceRequest) {
        return OfficialAbsenceGetRequestDto.builder()
                .id(officialAbsenceRequest.getId())
                .clubMemberId(officialAbsenceRequest.getClubMember().getId())
                .scheduleId(officialAbsenceRequest.getSchedule().getId())
                .officialAbsenceContent(officialAbsenceRequest.getOfficialAbsenceContent())
                .build();
    }

    @Transactional
    public void acceptResponse(Long officialAbsenceId) {
        try {
            OfficialAbsenceRequest officialAbsenceRequest = officialAbsenceRequestRepository.findById(officialAbsenceId)
                    .orElseThrow(() -> new NoSuchElementException("No OfficialAbsenceRequest found for officialAbsenceId: " + officialAbsenceId));

            Schedule schedule = scheduleRepository.findById(officialAbsenceRequest.getSchedule().getId())
                    .orElseThrow(() -> new NoSuchElementException("No Schedule found for scheduleId: " + officialAbsenceRequest.getSchedule().getId()));

            ClubMember clubMember = clubMemberRepository.findById(officialAbsenceRequest.getClubMember().getId())
                    .orElseThrow(() -> new NoSuchElementException("No ClubMember found for clubMemberId: " + officialAbsenceRequest.getClubMember().getId()));

            Long scheduleId = schedule.getId();
            Long clubMemberId = clubMember.getId();

            ScheduleAttendanceResult scheduleAttendanceResult = scheduleAttendanceResultRepository
                    .findByScheduleIdAndClubMemberId(scheduleId, clubMemberId);

            if (scheduleAttendanceResult != null) {
                //공결 수락
                scheduleAttendanceResult.setAttendanceType(OFFICIAL_ABSENCE);
                logger.warn("AttendanceType changed.");
                //공결 신청 삭제
                /**hard or soft delete??**/
                officialAbsenceRequestRepository.delete(officialAbsenceRequest);
                logger.warn("OfficialAbsenceRequest deleted.");
            } else {
                logger.warn("No ScheduleAttendanceResult found for scheduleId: {} and clubMemberId: {}", scheduleId, clubMemberId);
            }
        } catch (NoSuchElementException e) {
            logger.warn("Error processing acceptResponse: {}", e.getMessage());
        }
    }
    @Transactional
    public void denyResponse(Long officialAbsenceId) { // 공결 신청 거절
        try {
            OfficialAbsenceRequest officialAbsenceRequest = officialAbsenceRequestRepository.findById(officialAbsenceId)
                    .orElseThrow(() -> new NoSuchElementException("No OfficialAbsenceRequest found for officialAbsenceId: " + officialAbsenceId));

            Schedule schedule = scheduleRepository.findById(officialAbsenceRequest.getSchedule().getId())
                    .orElseThrow(() -> new NoSuchElementException("No Schedule found for scheduleId: " + officialAbsenceRequest.getSchedule().getId()));

            ClubMember clubMember = clubMemberRepository.findById(officialAbsenceRequest.getClubMember().getId())
                    .orElseThrow(() -> new NoSuchElementException("No ClubMember found for clubMemberId: " + officialAbsenceRequest.getClubMember().getId()));

            Long scheduleId = schedule.getId();
            Long clubMemberId = clubMember.getId();

            ScheduleAttendanceResult scheduleAttendanceResult = scheduleAttendanceResultRepository
                    .findByScheduleIdAndClubMemberId(scheduleId, clubMemberId);

            if (scheduleAttendanceResult != null) {
                //공결 거부
                scheduleAttendanceResult.setAttendanceType(ABSENCE);
                logger.warn("AttendanceType changed.");
                //공결 신청 삭제
                /**hard or soft delete??**/
                officialAbsenceRequestRepository.delete(officialAbsenceRequest);
                logger.warn("OfficialAbsenceRequest deleted.");
            } else {
                logger.warn("No ScheduleAttendanceResult found for scheduleId: {} and clubMemberId: {}", scheduleId, clubMemberId);
            }
        } catch (NoSuchElementException e) {
            logger.warn("Error processing acceptResponse: {}", e.getMessage());
        }
    }
}
