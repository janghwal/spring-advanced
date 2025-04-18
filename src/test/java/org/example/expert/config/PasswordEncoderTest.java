package org.example.expert.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class PasswordEncoderTest {

    @InjectMocks
    private PasswordEncoder passwordEncoder;

    @Test
    void PasswordEncoder_matches_true() {
        // given
        String rawPassword = "testPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // when
//        boolean matches = passwordEncoder.matches(encodedPassword, rawPassword);
        // PasswordEncoder의 matches는 matches(String rawPassword, String encodedPassword)와 같은 형태로 되어있다.
        // 첫번째 매개변수로 기존 비밀번호 즉 인코딩 되지 않은 비밀번호, 두번째 매개변수로 인코딩 되어 저장되어있는 비밀번호를 비교하는 함수이므로
        // rawPassword와 encodedPassword가 알맞은 위치에 들어갈 수 있도록 두 매개 변수의 자리를 바꿔준다.
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);

        // then
        assertTrue(matches);
    }
}
