package GDG.whatssue.domain.user.repository;

import GDG.whatssue.domain.user.entity.PhoneCertNum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface PhoneCertNumRepository extends CrudRepository<PhoneCertNum, String> {
    Optional<PhoneCertNum> findById(String id);
}
