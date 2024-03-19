package GDG.whatssue.domain.club.entity;


import GDG.whatssue.domain.file.entity.UploadFile;
import GDG.whatssue.domain.member.entity.ClubJoinRequest;
import GDG.whatssue.domain.member.entity.ClubMember;
import GDG.whatssue.domain.schedule.entity.Schedule;
import GDG.whatssue.global.common.BaseEntity;
import jakarta.persistence.*;
import java.util.List;

import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
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
    private Integer memberMaxValue;

    @Column
    private String contactMeans;

    @OneToOne(mappedBy = "club")
    private UploadFile profileImage;

    @Column(nullable = false)
    private boolean isActivateCode;

    @Column(nullable = false)
    private boolean isJoinStatus;

    @Column(nullable = false)
    private String privateCode;

    @OneToMany(mappedBy = "club")
    private List<ClubMember> clubMemberList;

    @OneToMany(mappedBy = "club")
    private List<ClubJoinRequest> clubJoinRequestList;

    @OneToMany(mappedBy = "club")
    private List<Schedule> scheduleList;

    // ActivateCode 값 update
    public void updateActivateCode(boolean isActivateCode) {
        this.isActivateCode = isActivateCode;
    }

    //PrivateCode 값 update
    public void updatePrivateCode(String privateCode) {
        this.privateCode = privateCode;
    }

    //isJoinStatus 값 update
    public void updateIsJoinStatus(boolean isJoinStatus) {
        this.isJoinStatus = isJoinStatus;
    }

}
