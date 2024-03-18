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


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    @PostMapping("/join")
    public ResponseEntity joinClub(@Valid @RequestBody ClubJoinRequestDto requestDto) {
        //dto size 예외처리 TODO
        //현재 로그인 id parameter로 받아오기 TODO
        Long userId = 1L;

        clubMemberService.addClubJoinRequest(userId, requestDto);

        return new ResponseEntity("ok", HttpStatus.OK);
    }
}
