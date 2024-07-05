package com.wagu.wafl.api.domain.post.repository;

import com.wagu.wafl.api.domain.post.entity.OttTag;
import com.wagu.wafl.api.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p JOIN FETCH p.user where p.ottTag in(:tags) and p.isActive = true order by p.createdAt desc")
    List<Post> findAllByOttTagIn(@Param("tags") List<OttTag> tags);

    @Query("SELECT p FROM Post p JOIN FETCH p.user where p.isActive = true order by p.createdAt desc")
    List<Post> findAllByCreatedAtAndIsActive();

}
