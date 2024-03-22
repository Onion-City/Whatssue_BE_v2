package GDG.whatssue.domain.member.controller;

import GDG.whatssue.domain.member.dto.ClubJoinRequestDto;
import GDG.whatssue.domain.member.service.ClubMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 모임 가입 신청 : [POST] - /api/clubs/{clubId}/join TODO
 * 신청 내역 조회 : [GET] - /api/clubs/{clubId}/join TODO
 * 신청 취소 : [DELETE] - /api/clubs/{clubId}/join-cancle TODO
 * 가입 요청 조회 : [GET] - /api/clubs/{clubId}/join-requests TODO
 * 가입 요청 수락 : [POST] - /api/clubs/{clubId}/join-requests/{clubJoinRequestId}/accept TODO
 * 가입 요청 거절 : [POST] - /api/club/{clubId}/join-requests/{clubJoinRequestId}/deny TODO
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs/{clubId}")
public class MemberJoinController {

    private final ClubMemberService clubMemberService;

    @PostMapping("/join")
    public ResponseEntity joinClub(@Valid @RequestBody ClubJoinRequestDto requestDto) {
        //현재 로그인 id parameter로 받아오기 & 예외처리 TODO
        Long userId = 1L;

        //validation 예외처리 TODO

        clubMemberService.addClubJoinRequest(userId, requestDto);

        return new ResponseEntity("ok", HttpStatus.OK);
    }
}
