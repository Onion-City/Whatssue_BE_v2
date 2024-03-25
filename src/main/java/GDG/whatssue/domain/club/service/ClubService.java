package GDG.whatssue.domain.club.service;

import GDG.whatssue.domain.club.dto.ClubCreateRequest;
import GDG.whatssue.domain.club.dto.ClubUpdateRequest;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ClubService {

    public Long createClub(Long userId, ClubCreateRequest requestDto, MultipartFile profileImage) throws IOException;
    public void updateClub(Long clubId, ClubUpdateRequest requestDto, MultipartFile profileImage) throws IOException;

    public void updateClubCode(Long clubId);
}
