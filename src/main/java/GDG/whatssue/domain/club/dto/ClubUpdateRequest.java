package GDG.whatssue.domain.club.dto;

import lombok.Getter;

@Getter
public class ClubUpdateRequest {

    String clubName;
    String clubInfo;
    Boolean isPrivate;
    String contactMeans;
}
