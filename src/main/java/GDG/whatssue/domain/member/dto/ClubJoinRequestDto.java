package GDG.whatssue.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubJoinRequestDto {

    @NotNull
    private long clubId;

    @Size(min = 6, max = 6)
    private String privateCode;
}
