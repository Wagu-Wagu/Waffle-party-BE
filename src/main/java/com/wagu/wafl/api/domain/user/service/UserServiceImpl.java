package com.wagu.wafl.api.domain.user.service;

import com.wagu.wafl.api.common.exception.AlertException;
import com.wagu.wafl.api.common.exception.UserException;
import com.wagu.wafl.api.common.message.ExceptionMessage;
import com.wagu.wafl.api.config.S3Config;
import com.wagu.wafl.api.config.WaguConfig;
import com.wagu.wafl.api.domain.alert.entity.Alert;
import com.wagu.wafl.api.domain.alert.repository.AlertRepository;
import com.wagu.wafl.api.domain.comment.entity.Comment;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.s3.service.S3ServiceImpl;
import com.wagu.wafl.api.domain.user.dto.request.OnboardRequestDTO;
import com.wagu.wafl.api.domain.user.dto.response.GetMyCommentResponseDTO;
import com.wagu.wafl.api.domain.user.dto.response.GetMyInfoResponseDTO;
import com.wagu.wafl.api.domain.user.dto.response.GetMyNewsResponseDTO;
import com.wagu.wafl.api.domain.user.dto.response.GetMyPostResponseDTO;
import com.wagu.wafl.api.domain.user.entity.User;
import com.wagu.wafl.api.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.val;

import org.apache.catalina.util.ToStringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.wagu.wafl.api.domain.user.dto.request.EditUserNickNameRequestDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final S3Config s3Config;
    private final WaguConfig waguConfig;
    private final UserRepository userRepository;
    private final S3ServiceImpl s3ServiceImpl;
    private final AlertRepository alertRepository;

    @Transactional
    @Override
    public void onboard(Long userId, OnboardRequestDTO request) {
        User user = findUser(userId);

        if(user.getNickName()!=null) {
            throw new UserException(ExceptionMessage.ALREADY_DID_ONBOARD.getMessage(), HttpStatus.BAD_REQUEST);
        }

        val sameNickNameUser = userRepository.findByNickName(request.nickName());
        if(sameNickNameUser.isPresent()) {
            throw new UserException(ExceptionMessage.ALREADY_EXIST_NICKNAME.getMessage(), HttpStatus.BAD_REQUEST);
        }

        user.setNickName(request.nickName());
    }

    @Override
    public boolean isOnboard(Long userId) {
        User user = findUser(userId);

        return !Objects.equals(user.getNickName(), null);
    }

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

    @Transactional
    @Override
    public void editUserImage(Long userId, MultipartFile requestFile) {
        User user = findUser(userId);

        if(checkImageFileEmpty(requestFile)) {
            user.setUserImage("");
            return;
        }
        String userImageS3URL = s3ServiceImpl.uploadImage(requestFile, s3Config.getUserImageFolderName()); //todo - 동일이미지 업로드
        String splitedURL = splitUserURL(userImageS3URL);
        user.setUserImage(splitedURL);
    }

    @Override
    public GetMyInfoResponseDTO getMyInfo(Long userId) {
        User user = findUser(userId);

        return GetMyInfoResponseDTO.of(user, waguConfig.getWaguVersion());
    }

    @Override
    public List<GetMyPostResponseDTO> getMyPosts(Long userId) {
        val user = userRepository.findUserAndPostOrderByCreatedAt(userId);

        if(!user.isPresent()) {
            return null;
        }

        List<Post> posts = user.get().getPosts();
        return posts.stream().map(p -> {
            return GetMyPostResponseDTO.of(p, user.get().getNickName());
        }).collect(Collectors.toList());
    }

    @Override
    public List<GetMyCommentResponseDTO> getMyComments(Long userId) {
        val user = userRepository.findUserAndCommentOrderByCreatedAt(userId);

        if(user.isEmpty()) {
            return null;
        }

        List<Comment> comments = user.get().getComments();
        return comments.stream().map((c) -> {
            return GetMyCommentResponseDTO.of(c.getPost(), c);
        }).collect(Collectors.toList());
    }

    @Override
    public List<GetMyNewsResponseDTO> getMyNews(Long userId) {
        User user = findUser(userId);
        List<Alert> sortedAlerts = user.getAlerts().stream()
                .sorted(Comparator.comparing(Alert::getIsRead)
                        .thenComparing(Comparator.comparing(Alert::getModifiedAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed()))
                .toList();

        return sortedAlerts.stream()
                .map(alert -> GetMyNewsResponseDTO.of(alert.getAlertType(), alert))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void checkMyNews(Long userId, Long alertId) {
        Alert alert = findAlert(alertId);
        validateAlertOwner(userId, alert);
        alert.setIsRead(true);
    }

    private void validateAlertOwner(Long userId, Alert alert) {
        if(!(Objects.equals(alert.getUser().getId(), userId))) {
            throw new AlertException(ExceptionMessage.IS_NOT_ALERT_OWNER.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private boolean checkImageFileEmpty(MultipartFile postImage) {
        String originFileName = postImage.getOriginalFilename();

        if (Objects.equals(originFileName, "")) {
            return true;
        }
        return false;
    }

    private String splitUserURL(String UserS3URL) {
        String url = UserS3URL.split(s3Config.getUserS3ImageBaseURL())[1];
        return url;
    }

    private User findUser(Long userId) { //todo - findEntity 한번에 클래스로 모아, Public으로 빼는거 어떤지
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
    }

    private Alert findAlert(Long alertId) {
        return alertRepository.findById(alertId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_ALERT.getMessage()));
    }
}
