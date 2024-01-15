package GDG.whatssue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="CheckList")
public class CheckList {
    @Id
    private Long id;

    @Column(nullable = false)
    private Long clubMemberId;

    @Column(nullable = false)
    private int checkCount;

    @Column(nullable = false)
    private int absentCount;

    @Column(nullable = false)
    private int officialCount;
}
