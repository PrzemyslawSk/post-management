package com.przemyslawsk.postmanagement.dto;

import com.przemyslawsk.postmanagement.model.Post;

public class PostMapper {
    public static PostDTO mapPostToDTO(Post post) {
        return PostDTO.builder()
                .title(post.getTitle())
                .description(post.getDescription())
                .image(post.getImage())
                .build();
    }

    public static Post mapToPost(PostDTO postDTO) {
        return Post.builder()
                .title(postDTO.getTitle())
                .description(postDTO.getDescription())
                .image(postDTO.getImage())
                .build();
    }
}
