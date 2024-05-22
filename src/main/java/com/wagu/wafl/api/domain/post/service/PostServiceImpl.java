package com.wagu.wafl.api.domain.post.service;

import com.wagu.wafl.api.domain.post.dto.response.OttPostsListResponseDTO;
import com.wagu.wafl.api.domain.post.entity.OttTag;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    public List<OttPostsListResponseDTO> getOttPosts(List<OttTag> request) {

        if(request.isEmpty()) {
            return postRepository.findAllByCreatedAtAndIsActive().stream()
                    .map((p) -> OttPostsListResponseDTO.of(p,
                            p.getUser().getNickName()))
                    .toList();
        }

        return postRepository.findAllByOttTagIn(request).stream()
                .map((p) -> OttPostsListResponseDTO.of(p,
                            p.getUser().getNickName()))
                .toList();
    }
}
