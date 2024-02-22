package GDG.whatssue.repository;

import GDG.whatssue.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserNick(String userNick);


}
