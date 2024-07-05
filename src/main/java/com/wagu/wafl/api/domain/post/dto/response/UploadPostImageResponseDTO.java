package com.wagu.wafl.api.domain.post.dto.response;

import com.wagu.wafl.api.domain.post.util.PhotoesParsingToList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Builder;

@Builder
public record UploadPostImageResponseDTO(List<String> photoes){
    public static UploadPostImageResponseDTO of(String photoes) {
        List<String> photoList = new ArrayList<>();

        if(!Objects.isNull(photoes) &&!photoes.isBlank()) {
            photoList = PhotoesParsingToList.toList(photoes);
        }
        return UploadPostImageResponseDTO.builder()
                .photoes(photoList)
                .build();
    }
}
