package GDG.whatssue.domain.club.controller;

import GDG.whatssue.domain.club.dto.ClubCreateRequest;
import GDG.whatssue.domain.club.dto.ClubUpdateRequest;
import GDG.whatssue.domain.club.dto.ClubCreateResponse;
import GDG.whatssue.domain.club.service.ClubService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 가입한 클럽 조회 : [GET] - /api/clubs TODO
 * 클럽 생성 : [POST] - /api/clubs TODO
 * 클럽 정보 수정  [PATCH] - /api/clubs/{clubId}/info
 * 클럽 삭제 : [DELETE] - /api/clubs/{clubId}
 * 클럽 초대코드 갱신 : [POST] - /api/clubs/{clubId}/private-code
 * 클럽 초대코드 조회 : [GET] - /api/clubs/{clubId}/private-code
 */
@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubServiceImpl;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createClub(
        @RequestPart("request") ClubCreateRequest request,
        @RequestPart("profileImage") MultipartFile profileImage) throws IOException {

        //user id 받아오기 & 예외처리 TODO
        long userId = 1L;

        //Validation 및 예외처리 TODO
        Long clubId = clubServiceImpl.createClub(userId, request, profileImage);

        ClubCreateResponse clubCreateResponse = new ClubCreateResponse();
        clubCreateResponse.setClubId(clubId);
        return ResponseEntity.status(HttpStatus.OK).body(clubCreateResponse);
    }

    @PatchMapping(value = "/{clubId}",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity updateClub(@PathVariable Long clubId,
        @RequestPart("request")ClubUpdateRequest request,
        @RequestPart("profileImage") MultipartFile profileImage) throws IOException {

        clubServiceImpl.updateClub(clubId, request, profileImage);

        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping("/{clubId}/private-code")
    public ResponseEntity updatePrivateCode(@PathVariable Long clubId) {
        clubServiceImpl.updateClubCode(clubId);

        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

//    @DeleteMapping
//    public ResponseEntity deleteClub() {
//        //TODO
//        return null;
//    }
}
