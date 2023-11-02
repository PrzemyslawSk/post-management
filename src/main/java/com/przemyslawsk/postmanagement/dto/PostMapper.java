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

}
