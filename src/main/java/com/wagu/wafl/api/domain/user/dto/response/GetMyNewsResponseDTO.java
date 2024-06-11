package com.wagu.wafl.api.domain.user.dto.response;

import com.wagu.wafl.api.domain.alert.entity.Alert;
import com.wagu.wafl.api.domain.alert.entity.AlertType;
import lombok.Builder;

import java.util.Collections;
import java.util.List;

@Builder
public record GetMyNewsResponseDTO(
        List<GetMyNewsResponseVO> getMyNewsResponseVOs
) {
    public static GetMyNewsResponseDTO of (AlertType alertType, Alert alert){
        return GetMyNewsResponseDTO.builder()
                .getMyNewsResponseVOs(Collections.singletonList(GetMyNewsResponseVO.of(alertType, alert)))
                .build();
    }
}


@Builder
record GetMyNewsResponseVO(
        AlertType alertType,
        Long postId,
        String content,
        Long newAlertCount,
        boolean isRead
) {
    public static GetMyNewsResponseVO of(AlertType alertType, Alert alert) {
        return GetMyNewsResponseVO.builder()
                .alertType(alertType)
                .postId(alert.getPost().getId())
                .content(alert.getContent())
                .newAlertCount(alert.getNewAlertCount())
                .isRead(alert.getIsRead())
                .build();
    }
}
