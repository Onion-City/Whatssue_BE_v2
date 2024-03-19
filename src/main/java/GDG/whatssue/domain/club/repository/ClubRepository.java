package GDG.whatssue.domain.club.repository;

import GDG.whatssue.domain.club.entity.Club;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {

    boolean existsByPrivateCode(String clubCode);
}
