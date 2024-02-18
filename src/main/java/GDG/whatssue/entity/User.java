package GDG.whatssue.entity;

import jakarta.persistence.*;
import java.util.List;

import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String userNick;

    @Column(nullable = false)
    private String userPw;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userPhone;

    @Column(nullable = false)
    private String role;
    @OneToMany(mappedBy = "user")
    private List<ClubJoinRequest> clubJoinRequestList;

}
