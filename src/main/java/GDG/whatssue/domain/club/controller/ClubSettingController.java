package GDG.whatssue.domain.club.controller;

import GDG.whatssue.domain.club.service.ClubSettingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/club_setting")
public class ClubSettingController {
    private final ClubSettingService clubSettingService;



    // 모임 가입 신청 암호 코드 활성/비활성
    @PostMapping(value = "/clubcode/{clubId}")
    @Operation(summary = "모임 가입 코드 활성화/비활성화")
    @PreAuthorize("hasRole('ROLE_'+#clubId+'MANAGER')")
    public ResponseEntity clubCodeActivate( @PathVariable Long cludId){

        return clubSettingService.isActivateCode(cludId);
    }


    // 모임 가입 암호 코드 갱신
    @PatchMapping(value = "/clubcode/{clubId}")
    @Operation(summary = "모임 가입 코드 갱신")
    @PreAuthorize("hasRole('ROLE_'+#clubId+'MANAGER')")
    public ResponseEntity clubCodeRenewal( @PathVariable Long cludId ){

        return clubSettingService.renewalClubCode(cludId);
    }

    // 모임 가입 신청 여닫기,가입 신청 조차를 안받는,status 변경
    @PatchMapping(value = "/clubstatus/{clubId}")
    @Operation(summary = "모임 가입 신청 여닫기")
    @PreAuthorize("hasRole('ROLE_'+#clubId+'MANAGER')")
    public ResponseEntity clubStatusChange( @PathVariable Long cludId ){

        return clubSettingService.changeClubStatus(cludId);
    }


}
