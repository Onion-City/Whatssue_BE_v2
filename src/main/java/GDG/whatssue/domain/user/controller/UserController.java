package GDG.whatssue.domain.user.controller;

import GDG.whatssue.domain.user.dto.SignUpRequestDto;
import GDG.whatssue.domain.user.dto.UserDto;
//import GDG.whatssue.domain.user.entity.PrincipalDetails;
//import GDG.whatssue.domain.user.service.UserService;
import GDG.whatssue.domain.user.entity.KakaoDetails;
import GDG.whatssue.domain.user.service.CustomOauth2Service;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final CustomOauth2Service customOauth2Service;


    @GetMapping({ "", "/" })
    @Operation(summary = "메인 페이지")
    public String index() {
        return "index";
    }

    @GetMapping("/api/get/user")
    public ResponseEntity getUserProfile(@AuthenticationPrincipal KakaoDetails kakaoDetails) {
        UserDto userDto  = customOauth2Service.getUserInfo(kakaoDetails);
        return ResponseEntity.status(200).body(userDto);
    }

    @PostMapping ("/api/signUp")
    public ResponseEntity signUpWithPhoneNumAndName(@AuthenticationPrincipal KakaoDetails kakaoDetails, SignUpRequestDto request) {
        UserDto userDto = customOauth2Service.signUp(kakaoDetails,request);
        return ResponseEntity.status(200).body(userDto);
    }
}
