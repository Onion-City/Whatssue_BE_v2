package GDG.whatssue.domain.club.service;

import GDG.whatssue.domain.club.entity.Club;
import GDG.whatssue.domain.club.exception.ClubErrorCode;
import GDG.whatssue.domain.club.repository.ClubRepository;
import GDG.whatssue.global.error.CommonException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

/**예외 처리 안되어 controller 상에서 메소드 실행 후 완료로 표시될 수 있음**/
@Service
@RequiredArgsConstructor
public class ClubSettingService {
    private final ClubRepository clubRepository;
    private static final Logger logger = LoggerFactory.getLogger(ClubSettingService.class);


    private String generateRandomClubCode() {
        Random random = new Random();
        // 랜덤한 알파벳 대문자와 0에서 9 사이의 랜덤한 정수를 섞어 클럽 코드로 사용
        return "" + (char) ('A' + random.nextInt(26)) + random.nextInt(10)
                + (char) ('A' + random.nextInt(26)) + random.nextInt(10)
                + (char) ('A' + random.nextInt(26)) + random.nextInt(10);
    }


    public ResponseEntity isActivateCode(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CommonException(ClubErrorCode.CLUB_NOT_FOUND_ERROR));
        boolean isActivateCode = club.isActivateCode();

        if (club != null) {
            //isActivatecode 가 false 일 경우 true로, true일 경우 false로 변경
            if (!isActivateCode) {
                // update 메소드 사용
                // 활성화
                club.updateActivateCode(true);
                clubRepository.save(club);
                //response
                return ResponseEntity.status(200).body("모임 가입 코드 활성화 완료");
            } else {
                // 비활성화
                club.updateActivateCode(false);
                clubRepository.save(club);
                return ResponseEntity.status(200).body("모임 가입 코드 비활성화 완료");

            }
        } else {
            logger.warn("모임을 찾을 수 없습니다.");
            return ResponseEntity.status(404).body("모임을 찾을 수 없습니다.");
        }

    }

    public ResponseEntity renewalClubCode(Long cludId) {
        Club club = clubRepository.findById(cludId)
                .orElseThrow(() -> new CommonException(ClubErrorCode.CLUB_NOT_FOUND_ERROR));
        String privateCode = club.getPrivateCode();
        if (club != null) {
            do {
                privateCode = generateRandomClubCode();
            } while (clubRepository.existsByPrivateCode(privateCode)); // 중복 체크

            club.updatePrivateCode(privateCode);
            clubRepository.save(club);
            //가입 코드를 return
            return ResponseEntity.status(200).body(privateCode);

        } else {
            logger.warn("모임을 찾을 수 없습니다.");
            return ResponseEntity.status(404).body("모임을 찾을 수 없습니다.");
        }
    }

    public ResponseEntity changeClubStatus(Long cludId) {
        Club club = clubRepository.findById(cludId)
                .orElseThrow(() -> new CommonException(ClubErrorCode.CLUB_NOT_FOUND_ERROR));
        if (club != null) {
            //isJoinStatus 가 false 일 경우 true로, true일 경우 false로 변경
            if (!club.isJoinStatus()) {
                // update 메소드 사용
                // 활성화
                club.updateIsJoinStatus(true);
                clubRepository.save(club);
                //response
                return ResponseEntity.status(200).body("모임 가입 신청 여닫기 완료");
            } else {
                // 비활성화
                club.updateIsJoinStatus(false);
                clubRepository.save(club);
                return ResponseEntity.status(200).body("모임 가입 신청 여닫기 완료");

            }
        } else {
            logger.warn("모임을 찾을 수 없습니다.");
            return ResponseEntity.status(404).body("모임을 찾을 수 없습니다.");
        }

    }
}
