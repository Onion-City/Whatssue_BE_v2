package GDG.whatssue.domain.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 멤버 권한 수정 ???
 * 멤버 추방 : [DELETE] - /api/clubs/{clubId}/members/{memberId}
 * 멤버 상세조회(일반멤버) : [GET] - /api/clubs/{clubId}/members/{memberId}
 * 멤버 상세조회(관리자) : [GET] - /api/clubs/{clubId}/members/{memberId}/manager
 * 멤버 목록 조회 : [GET] - /api/clubs/{clubId}/members
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs/{clubId}/members")
public class ClubMemberController {

}
