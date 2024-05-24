package com.wagu.wafl.api.domain.post.dto.request;

import com.wagu.wafl.api.domain.post.entity.OttTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreatePostRequestDTO(
        @NotNull(message = "OTT 카테고리는 null일 수 없습니다.")
        OttTag ottTag,
        @NotNull(message = "글 제목은 null일 수 없습니다.")
        @NotBlank(message = "글 제목은 빈스트링 일 수 없습니다.")
        String title,
        @NotNull(message = "글 본문은 null일 수 없습니다.")
        @NotBlank(message = "글 본문은 빈스트링 일 수 없습니다.")
        String content,
        List<MultipartFile> postImages
) {
}
