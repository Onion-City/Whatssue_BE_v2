package GDG.whatssue.domain.club.service.impl;

import GDG.whatssue.domain.club.dto.ClubCreateRequest;
import GDG.whatssue.domain.club.dto.ClubUpdateRequest;
import GDG.whatssue.domain.club.entity.Club;
import GDG.whatssue.domain.club.exception.ClubErrorCode;
import GDG.whatssue.domain.club.repository.ClubRepository;
import GDG.whatssue.domain.club.service.ClubService;
import GDG.whatssue.domain.file.entity.UploadFile;
import GDG.whatssue.domain.file.repository.FileRepository;
import GDG.whatssue.domain.file.service.FileUploadService;
import GDG.whatssue.domain.member.entity.ClubMember;
import GDG.whatssue.domain.member.repository.ClubMemberRepository;
import GDG.whatssue.domain.user.repository.UserRepository;
import GDG.whatssue.global.common.Role;
import GDG.whatssue.global.error.CommonException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private static String PROFILE_IMAGE_DIRNAME = "clubProfileImage";
    private final ClubRepository clubRepository;
    private final FileRepository fileRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    @Override
    public Long createClub(Long userId, ClubCreateRequest requestDto, MultipartFile profileImage) throws IOException {
        //초대코드 추가하여 클럽 생성
        Club savedClub = clubRepository.save(createDtoToClubEntity(requestDto));

        //profileImage를 첨부하지 않았을 시 기본 이미지 처리 TODO

        String originalFileName = profileImage.getOriginalFilename();
        //profileImage fileRepository 및 s3에 저장 처리
        String storeFileName = fileUploadService.saveFile(profileImage, PROFILE_IMAGE_DIRNAME);
        fileRepository.save(UploadFile.builder()
            .club(savedClub)
            .uploadFileName(originalFileName)
            .storeFileName(storeFileName).build());

        //클럽을 생성한 유저 관리자로 추가
        clubMemberRepository.save(
            ClubMember.builder()
            .club(savedClub)
            .user(userRepository.findById(userId).get())
            .role(Role.MANAGER).build());

        return savedClub.getId();
    }

    @Override
    public void updateClub(Long clubId, ClubUpdateRequest requestDto, MultipartFile profileImage) throws IOException {
        Club club = clubRepository.findById(clubId)
            .orElseThrow(() -> new CommonException(ClubErrorCode.CLUB_NOT_FOUND_ERROR));

        //값 업데이트
        club.updateClub(requestDto);
        clubRepository.save(club);

        //만약 프로필 사진도 변경되었다면 변경처리 TODO
/*
        if (profileImage != null) {
            fileUploadService.deleteFile();
            String storeFileName = fileUploadService.saveFile(profileImage, PROFILE_IMAGE_DIRNAME);
            //FileRepo에 저장 TODO
        }
*/

    }

    @Override
    public void updateClubCode(Long clubId) {
        Club club = clubRepository.findById(clubId)
            .orElseThrow(() -> new CommonException(ClubErrorCode.CLUB_NOT_FOUND_ERROR));

        club.createNewPrivateCode();
    }

    private Club createDtoToClubEntity(ClubCreateRequest requestDto) {
        Club club = requestDto.toEntity();
        club.createNewPrivateCode();

        return club;
    }
}

