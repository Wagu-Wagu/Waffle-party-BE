package com.wagu.wafl.api.domain.user.dto.response;

import com.wagu.wafl.api.domain.alert.entity.Alert;
import com.wagu.wafl.api.domain.alert.entity.AlertType;
import lombok.Builder;

import java.time.LocalDateTime;
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
        Long alertId,
        AlertType alertType,
        Long postId,
        String content,
        Long newAlertCount,
        boolean isRead,
        LocalDateTime modifiedAt
) {
    public static GetMyNewsResponseVO of(AlertType alertType, Alert alert) {
        GetMyNewsResponseVO getMyNewsResponseVO = null;
        if (alertType.equals(AlertType.COMMENT)) {
            getMyNewsResponseVO = GetMyNewsResponseVO.builder()
                    .alertId(alert.getId())
                    .alertType(alertType)
                    .postId(alert.getPost().getId())
                    .content(alert.getPost().getTitle())
                    .newAlertCount(alert.getNewAlertCount())
                    .isRead(alert.getIsRead())
                    .modifiedAt(alert.getModifiedAt())
                    .build();
        }
        if (alertType.equals(AlertType.REPLY)) {
            getMyNewsResponseVO = GetMyNewsResponseVO.builder()
                    .alertId(alert.getId())
                    .alertType(alertType)
                    .postId(alert.getComment().getPost().getId())
                    .content(alert.getComment().getContent())
                    .newAlertCount(alert.getNewAlertCount())
                    .isRead(alert.getIsRead())
                    .modifiedAt(alert.getModifiedAt())
                    .build();
        }
        return getMyNewsResponseVO;
    }
}
