package GDG.whatssue.domain.club.exception;

import GDG.whatssue.global.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ClubErrorCode implements ErrorCode {
    CLUB_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "Club Not Found");
    private final HttpStatus httpStatus;
    private final String message;

}
