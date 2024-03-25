package GDG.whatssue.domain.club.entity;


import GDG.whatssue.domain.club.dto.ClubUpdateRequest;
import GDG.whatssue.domain.file.entity.UploadFile;
import GDG.whatssue.domain.member.entity.ClubJoinRequest;
import GDG.whatssue.domain.member.entity.ClubMember;
import GDG.whatssue.domain.schedule.entity.Schedule;
import GDG.whatssue.global.common.BaseEntity;
import GDG.whatssue.global.common.NamePolicy;
import jakarta.persistence.*;
import java.util.List;

import java.util.UUID;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Club extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;


    @Column(nullable = false)
    private String clubName;

    @Column(nullable = false)
    private String clubInfo;

    @Column(nullable = false)
    private boolean isPrivate;

    @Column
    private String contactMeans;

    @Column
    private boolean isActivateCode;

    @Column
    private boolean isJoinStatus;

    @Column(nullable = false)
    private String privateCode;

    @OneToOne(mappedBy = "club")
    private UploadFile profileImage;

    @OneToMany(mappedBy = "club")
    private List<ClubMember> clubMemberList;

    @OneToMany(mappedBy = "club")
    private List<ClubJoinRequest> clubJoinRequestList;

    @OneToMany(mappedBy = "club")
    private List<Schedule> scheduleList;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NamePolicy namePolicy;

    // ActivateCode 값 update
    public void updateActivateCode(boolean isActivateCode) {
        this.isActivateCode = isActivateCode;
    }

    //isJoinStatus 값 update
    public void updateIsJoinStatus(boolean isJoinStatus) {
        this.isJoinStatus = isJoinStatus;
    }

    public void createNewPrivateCode() {
        this.privateCode = UUID.randomUUID().toString().substring(0, 6);
    }

    public void updateClub(ClubUpdateRequest requestDto) {
        this.clubName = requestDto.getClubName();
        this.clubInfo = requestDto.getClubInfo();
        this.isPrivate = requestDto.getIsPrivate();
        this.contactMeans = requestDto.getContactMeans();
    }

    @Builder
    public Club(String clubName, String clubInfo, boolean isPrivate, String contactMeans, NamePolicy namePolicy) {
        this.clubName = clubName;
        this.clubInfo = clubInfo;
        this.isPrivate = isPrivate;
        this.contactMeans = contactMeans;
        this.namePolicy = namePolicy;
    }
}
