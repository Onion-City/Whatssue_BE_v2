package GDG.whatssue.domain.club.service;

import GDG.whatssue.domain.club.dto.ClubCreateRequest;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ClubService {

    public Long createClub(Long userId, ClubCreateRequest requestDto, MultipartFile profileImage) throws IOException;
    //delete 메서드
}
