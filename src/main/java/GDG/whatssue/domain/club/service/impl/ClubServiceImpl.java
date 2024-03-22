package GDG.whatssue.domain.club.service.impl;

import GDG.whatssue.domain.club.dto.ClubCreateRequest;
import GDG.whatssue.domain.club.entity.Club;
import GDG.whatssue.domain.club.repository.ClubRepository;
import GDG.whatssue.domain.club.service.ClubService;
import GDG.whatssue.domain.file.entity.UploadFile;
import GDG.whatssue.domain.file.repository.FileRepository;
import GDG.whatssue.domain.file.service.FileUploadService;
import GDG.whatssue.domain.member.entity.ClubMember;
import GDG.whatssue.domain.member.repository.ClubMemberRepository;
import GDG.whatssue.domain.user.repository.UserRepository;
import GDG.whatssue.global.common.Role;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final FileRepository fileRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    @Override
    public Long createClub(Long userId, ClubCreateRequest requestDto, MultipartFile profileImage) throws IOException {
        //profileImage fileRepository 및 s3에 저장 처리 TODO
        Club savedClub = clubRepository.save(requestDto.toEntity());

        String storeFileName = fileUploadService.saveFile(profileImage, "clubProfileImage");

        fileRepository.save(UploadFile.builder()
            .club(savedClub)
            .uploadFileName(profileImage.getOriginalFilename())
            .storeFileName(storeFileName).build());

        //클럽을 생성한 유저 관리자로 추가
        clubMemberRepository.save(
            ClubMember.builder()
            .club(savedClub)
            .user(userRepository.findById(userId).get())
            .role(Role.MANAGER).build());

        return savedClub.getId();
    }
}
