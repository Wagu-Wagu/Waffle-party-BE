package com.wagu.wafl.api.domain.post.dto.request;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record UploadPostImageRequestDTO(
        @NotNull(message = "파일은 최소 한장 이상만 업로드 가능합니다.")
        List<MultipartFile> postImages
) {
}
