package com.wagu.wafl.api.domain.user.sevice;


import com.wagu.wafl.api.domain.user.entity.AuthProvider;
import com.wagu.wafl.api.domain.user.entity.User;
import com.wagu.wafl.api.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }


    @DisplayName("유저 생성 테스트")
    @Test
    void 유저_생성_테스트() {
        // given
        User user = createUser();
        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        User savedUser = userRepository.save(user);
        //then
        assertAll(
                () -> assertEquals(user.getNickName(), savedUser.getNickName(), "사용자 이름이 일치해야 합니다."),
                () -> assertEquals(user.getEmail(), savedUser.getEmail(), "사용자 이메일이 일치해야 합니다.")
        );
    }


    private User createUser() {
        String email = "test@naver.com";

        return User
                .builder()
                .authProvider(createAuthProvider())
                .email(email)
                .build();
    }

    private AuthProvider createAuthProvider() {
        final String AUTH_PROVIDER_ID = "000000";
        final String PROVIDER_TYPE = "KAKAO";
        return AuthProvider.builder()
                .id(AUTH_PROVIDER_ID)
                .providerType(PROVIDER_TYPE)
                .build();
    }
}
