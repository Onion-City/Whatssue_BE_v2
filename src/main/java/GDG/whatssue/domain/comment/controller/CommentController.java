package GDG.whatssue.domain.comment.controller;

import GDG.whatssue.domain.comment.dto.CommentRequestDto;
import GDG.whatssue.domain.comment.entity.Comment;
import GDG.whatssue.domain.comment.repository.CommentRepository;
import GDG.whatssue.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;


@RestController
@RequestMapping("/api/{clubId}/member/{memberId}/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    @Operation(summary = "댓글 작성")
    @PostMapping("/post")
    public ResponseEntity createComment(CommentRequestDto dto, @PathVariable Long memberId) {
        commentService.createComment(memberId,dto);
        return ResponseEntity.status(200).body("ok");
    }

    @Operation(summary = "댓글 수정")
    @PatchMapping("/update")
    public void updateComment(CommentRequestDto dto, @PathVariable Long memberId) {
        commentService.updateComment(memberId,dto);
    }

    @Operation(summary = "댓글 삭제")
    public void deleteComment() {
        // 댓글 삭제
    }

    @Operation(summary = "댓글 조회")
    public void getComment() {
        // 댓글 조회
    }

    @Operation(summary = "댓글 목록 조회")
    public void getCommentList() {
        // 댓글 목록 조회
    }
}
