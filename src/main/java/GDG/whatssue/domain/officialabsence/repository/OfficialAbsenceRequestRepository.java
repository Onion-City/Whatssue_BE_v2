package GDG.whatssue.domain.officialabsence.repository;

import GDG.whatssue.domain.officialabsence.entity.OfficialAbsenceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficialAbsenceRequestRepository extends JpaRepository<OfficialAbsenceRequest, Long> {
    List<OfficialAbsenceRequest> findByIsChecked(boolean b);
}
