package GDG.whatssue.domain.post.repository;

import GDG.whatssue.domain.club.entity.Club;
import GDG.whatssue.domain.member.entity.ClubMember;
import GDG.whatssue.domain.post.entity.Post;
import GDG.whatssue.domain.post.entity.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);

    Page<Post> findByClubAndWriter(Club club, ClubMember clubMember, Pageable pageable);
    Page<Post> findByPostLikeList_ClubMemberAndClub(ClubMember clubMember, Club club, Pageable pageable);
}
