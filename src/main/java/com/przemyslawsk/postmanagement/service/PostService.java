package com.przemyslawsk.postmanagement.service;

import com.przemyslawsk.postmanagement.dto.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts();
    PostDTO getPostById(Long id);
    PostDTO createPost(PostDTO postDTO, MultipartFile file) throws IOException;
    PostDTO updatePost(Long id, PostDTO postDTO, MultipartFile file) throws IOException;
    void deletePost(Long id);
}
