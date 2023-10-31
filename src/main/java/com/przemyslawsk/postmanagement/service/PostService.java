package com.przemyslawsk.postmanagement.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts();
    PostDTO getPostById(Long Id);
    PostDTO createPost(PostDTO postDTO, MultipartFile file) throws IOException;
    PostDTO updatePost(Long id, PostDTO postDTO);
    void deletePost(Long id);
}
