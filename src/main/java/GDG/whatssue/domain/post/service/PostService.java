package GDG.whatssue.domain.post.service;

import static GDG.whatssue.domain.file.FileConst.POST_IMAGE_DIRNAME;

import GDG.whatssue.domain.club.entity.Club;
import GDG.whatssue.domain.club.repository.ClubRepository;
import GDG.whatssue.domain.file.entity.UploadFile;
import GDG.whatssue.domain.file.repository.FileRepository;
import GDG.whatssue.domain.file.service.FileUploadService;
import GDG.whatssue.domain.member.entity.ClubMember;
import GDG.whatssue.domain.member.repository.ClubMemberRepository;
import GDG.whatssue.domain.post.dto.AddPostRequest;
import GDG.whatssue.domain.post.dto.GetPostResponse;
import GDG.whatssue.domain.post.dto.UpdatePostRequest;
import GDG.whatssue.domain.post.entity.Post;
import GDG.whatssue.domain.post.exception.PostErrorCode;
import GDG.whatssue.domain.post.repository.PostRepository;
import GDG.whatssue.global.error.CommonException;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final PostRepository postRepository;
    private final FileUploadService fileUploadService;
    private final FileRepository fileRepository;

    @Transactional
    public void addPost(Long clubId, Long userId, AddPostRequest request, List<MultipartFile> postImages)
        throws IOException {
        Club club = clubRepository.findById(clubId).get();
        ClubMember writer = clubMemberRepository.findByClub_IdAndUser_UserId(clubId, userId).get();

        //게시글 db 저장
        Post post = request.toEntity(club, writer);
        postRepository.save(post);

        //이미지 s3 업로드, db 저장
        uploadPostImages(postImages, post);
    }

    public GetPostResponse getPost(Long postId) {
        //사진 이미지 반환 TODO

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new CommonException(PostErrorCode.EX7100));

        return GetPostResponse.builder()
            .postId(post.getId())
            .writerName(post.getWriter().getMemberName())
            .postCategory(post.getPostCategory())
            .postTitle(post.getPostTitle())
            .postContent(post.getPostContent()).build();
//            .writerProfileImage()
//            .uploadImage()

    }

    @Transactional
    public void uploadPostImages(List<MultipartFile> postImages, Post post) throws IOException {
        if (postImages != null) {
            for (MultipartFile postImage : postImages) {
                UploadFile imageFile = fileUploadService.uploadFile(postImage, POST_IMAGE_DIRNAME);
                post.addPostImageFile(imageFile);
                fileRepository.save(imageFile);
            }
        }
    }

    @Transactional
    public void deletePost(Long postId) throws IOException {
        Post post = postRepository.findById(postId).get();
        if(post ==null){
            //postId에 해당하는 게시글이 null일 경우 에러반환 TODO
        }

        postRepository.delete(post);
    }

    public void updatePost(Long clubId, Long memberId, Long postId, UpdatePostRequest request, List<MultipartFile> postImages) {
    //기존 post에 수정 사항을 적용하는 메소드 작성 TODO
    }
}
