package GDG.whatssue.domain.club.controller;

import GDG.whatssue.domain.club.dto.ClubCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 모임 조회 : [GET] - /api/clubs
 * 모임 생성 : [POST] - /api/clubs
 * 모임 삭제 : [DELETE] - /api/clubs/{clubId}
 * 모임 검색 : [GET] - /api/clubs?query=검색어
 */
@RestController
@RequestMapping("/api/clubs")
public class ClubController {

//    @GetMapping
//    public ResponseEntity getAllClub() {
//        //TODO
//        return null;
//    }
//

    @PostMapping
    public ResponseEntity createClub(@RequestBody ClubCreateRequest requestDto) {
        //예외처리 TODO
        //service단 TODO
        return null;
    }
//
//    @DeleteMapping
//    public ResponseEntity deleteClub() {
//        //TODO
//        return null;
//    }
//
//    @GetMapping
//    public ResponseEntity searchClub() {
//        //TODO
//        return null;
//    }
}
