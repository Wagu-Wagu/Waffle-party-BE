package com.wagu.wafl.api.domain.user.service;

import com.wagu.wafl.api.common.exception.UserException;
import com.wagu.wafl.api.common.message.ExceptionMessage;
import com.wagu.wafl.api.domain.user.entity.User;
import com.wagu.wafl.api.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.wagu.wafl.api.domain.user.dto.request.EditUserNickNameRequestDto;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void editUserNickName(Long userId, EditUserNickNameRequestDto request) {
        User user = findUser(userId);

        if(user.getNickName().equals(request.nickName())) {
            throw new UserException(ExceptionMessage.IS_THE_SAME_NICKNAME.getMessage(), HttpStatus.BAD_REQUEST);
        }

        val sameNickNameUser = userRepository.findByNickName(request.nickName());
        if(sameNickNameUser.isPresent()) {
            throw new UserException(ExceptionMessage.ALREADY_EXIST_NICKNAME.getMessage(), HttpStatus.BAD_REQUEST);
        }

        user.setNickName(request.nickName());
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
    } //todo - findEntity 한번에 클래스로 모아, Public으로 빼는거 어떤지

}
