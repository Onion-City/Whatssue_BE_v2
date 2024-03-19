package GDG.whatssue.domain.club.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ClubCreateRequest {

    private String clubName; //클럽 이름(필수)
    private String clubInfo; //클럽 소개(필수)
    private boolean isPrivate; //비공개 여부(필수)
    private MultipartFile profileImage; //대표 사진
    private int memberMaxValue; //최대 인원
    private String contactMeans; //연락수단
}
