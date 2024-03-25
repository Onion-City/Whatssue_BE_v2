package GDG.whatssue.domain.user.entity;


import jakarta.persistence.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Getter
@RedisHash(value = "PhoneCertNum", timeToLive = 10000)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class PhoneCertNum {
    @Id// phoneCert:Num
    @Indexed
    private String Id;
    private int certificationNum;
    private LocalDateTime createdAt;
}
