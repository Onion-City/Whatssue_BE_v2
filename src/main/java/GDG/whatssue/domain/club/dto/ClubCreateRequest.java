package GDG.whatssue.domain.club.dto;

import GDG.whatssue.domain.club.entity.Club;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@ToString
public class ClubCreateRequest {

    private String clubName; //클럽 이름(필수)
    private String clubInfo; //클럽 소개(필수)
    private Boolean isPrivate; //비공개 여부(필수)
    private int memberMaxValue; //최대 인원
    private String contactMeans; //연락수단
    private String privateCode;

    public Club toEntity() {
        return Club.builder()
            .clubName(this.clubName)
            .clubInfo(this.clubInfo)
            .isPrivate(this.isPrivate)
            .memberMaxValue(memberMaxValue)
            .contactMeans(contactMeans)
            .privateCode(createPrivateCode()).build();
    }

    private static String createPrivateCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
