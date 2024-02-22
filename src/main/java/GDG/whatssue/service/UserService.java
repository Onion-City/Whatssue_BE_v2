package GDG.whatssue.service;
import GDG.whatssue.dto.User.UserDto;
import GDG.whatssue.entity.PrincipalDetails;
import GDG.whatssue.entity.User;
import GDG.whatssue.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
//시큐리티 설정에서 loginProcessingUrl("/login");
//login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어있는 loadUserByUsername 함수가 실행
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void signUp(UserDto userDto) {
        User user = User.builder()
                .userNick(userDto.getUserNick())
                .userPw(userDto.getUserPw())
                .userEmail(userDto.getUserEmail())
                .userName(userDto.getUserName())
                .userPhone(userDto.getUserPhone())
                .role("ROLE_USER")
                .build();
        // 비밀번호 암호화 : 비밀번호 암호화가 안되어있으면 security로 로그인을 할 수 없음.
        user.setUserPw(passwordEncoder.encode(user.getUserPw()));
        userRepository.save(user);
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) {
        User user = userRepository.findByUserNick(userName);

        if(userRepository.findByUserNick(userName) == null)
            return null;

        PrincipalDetails userDetails = new PrincipalDetails(user);
        System.out.println("로그인 완료");
        return userDetails;

        // UserDetails가 return이 되면 시큐리티 session의 Authentication의 내부에 userDetail이 저장이 된다.
        // Session(내부 Authentication(내부 UserDetails))
    }

    public UserDto getUserInfo(PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();
        return UserDto.builder()
                .userNick(user.getUserNick())
                .userPw(user.getUserPw())
                .userEmail(user.getUserEmail())
                .userName(user.getUserName())
                .userPhone(user.getUserPhone())
                .build();
    }


}
