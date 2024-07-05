package GDG.whatssue.domain.member.service;

import GDG.whatssue.domain.club.entity.Club;
import GDG.whatssue.domain.club.entity.NamePolicy;
import GDG.whatssue.domain.club.repository.ClubRepository;
import GDG.whatssue.domain.file.entity.MemberProfileImage;
import GDG.whatssue.domain.file.service.FileUploadService;
import GDG.whatssue.domain.member.dto.*;
import GDG.whatssue.domain.member.entity.ClubMember;
import GDG.whatssue.domain.member.exception.ClubMemberErrorCode;
import GDG.whatssue.domain.member.repository.ClubMemberRepository;
import GDG.whatssue.domain.user.Error.UserErrorCode;
import GDG.whatssue.domain.user.entity.User;
import GDG.whatssue.domain.user.repository.UserRepository;
import GDG.whatssue.global.util.S3Utils;
import GDG.whatssue.global.error.CommonException;

import java.io.IOException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static GDG.whatssue.domain.club.entity.NamePolicy.REAL_NAME;
import static GDG.whatssue.domain.file.FileConst.MEMBER_PROFILE_IMAGE_DIRNAME;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubMemberService {
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;


    @Transactional
    public void modifyClubMember(Long clubId, Long userId, ClubMemberInfoDto requestDto) {

        ClubMember clubMember = findClubMemberByClubAndUser(clubId, userId).get();
        Club club = clubRepository.findById(clubId).get();

        String memberName;

        if (club.getNamePolicy() == REAL_NAME) memberName = clubMember.getUser().getUserName();
        else memberName = requestDto.getMemberName();

        clubMember.updateProfile(memberName,
                requestDto.getMemberIntro(),
                requestDto.isEmailPublic(),
                requestDto.isPhonePublic());

    }

    @Transactional
    public void setMemberProfile(Long clubId, Long userId, CreateMemberProfileRequest request) throws IOException {
        // 멤버 생성 및 역할 일반 멤버로 설정
        Club club = clubRepository.findById(clubId).get();
        NamePolicy namePolicy = club.getNamePolicy();


        ClubMember clubMember = clubMemberRepository.findById(getClubMemberId(clubId,userId)).get();

        if(!clubMember.isFirstVisit()) throw new CommonException(ClubMemberErrorCode.EX2201);

        // 멤버 프로필 이미지 저장
        MultipartFile multipartFile = request.getProfileImage();
        String storeFileName = fileUploadService.uploadFile(multipartFile, MEMBER_PROFILE_IMAGE_DIRNAME);
        String originalFileName = fileUploadService.getOriginalFileName(multipartFile);
        MemberProfileImage memberProfileImage = MemberProfileImage.of(originalFileName, storeFileName);

        // 멤버 프로필 정보  업데이트
        String memberName;
        if(namePolicy == REAL_NAME)
            memberName = clubMember.getUser().getUserName();
        else
            memberName = request.getMemberName();

        clubMember.updateProfile(request.getMemberIntro(), memberName, request.getIsEmailPublic(), request.getIsPhonePublic());
        clubMember.changeProfileImage(memberProfileImage);
        clubMember.setFirstVisitFalse();
    }

    // TDDO
    @Transactional
    public MemberProfileDto getMemberProfile(Long clubId, Long userId) {

        ClubMember clubMember = findClubMemberByClubAndUser(clubId, userId).get();

        User user = userRepository.findById(userId)
                .orElseThrow(() ->new CommonException(UserErrorCode.EX1100));

        String storeFileName = clubMember.getProfileImage().getStoreFileName();
        String memberProfileImage = S3Utils.getFullPath(storeFileName);

        MemberProfileDto profile = MemberProfileDto.builder()
                .memberId(clubMember.getId())
                .userName(user.getUserName())
                .userPhone(user.getUserPhone())
                .userEmail(user.getUserEmail())
                .memberName(clubMember.getMemberName())
                .memberIntro(clubMember.getMemberIntro())
                .role(clubMember.getRole())
                .isMemberEmailPublic(clubMember.isEmailPublic())
                .isMemberPhonePublic(clubMember.isPhonePublic())
                .profileImage(memberProfileImage)
                .build();

        return profile;
    }

    public MemberAuthInfoResponse getMemberAuthInfo(Long clubId, Long userId) {
        ClubMember member = clubMemberRepository.findMemberWithClub(clubId, userId);
        return new MemberAuthInfoResponse(member);
    }

    public ClubMemberDto getMemberIdAndRole(Long clubId, Long userId) {
        ClubMember clubMember = findClubMemberByClubAndUser(clubId, userId).get();
        return ClubMemberDto.builder()
                .memberId(clubMember.getId())
                .role(clubMember.getRole())
                .build();
    }

    public Long getClubMemberId(Long clubId, Long userId) {
        return findClubMemberByClubAndUser(clubId, userId).get().getId();
    }

    public Optional<ClubMember> findClubMemberByClubAndUser(Long clubId, Long userId) {
        return clubMemberRepository.findByClub_IdAndUser_UserId(clubId, userId);
    }

}