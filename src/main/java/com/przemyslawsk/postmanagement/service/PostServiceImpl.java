package com.przemyslawsk.postmanagement.service;

import com.przemyslawsk.postmanagement.dto.PostDTO;
import com.przemyslawsk.postmanagement.dto.PostMapper;
import com.przemyslawsk.postmanagement.exception.NotFoundException;
import com.przemyslawsk.postmanagement.model.Post;
import com.przemyslawsk.postmanagement.repository.PostRepository;
import com.przemyslawsk.postmanagement.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = repository.findAll();

        return posts.stream()
                .map(post -> PostDTO.builder()
                        .title(post.getTitle())
                        .description(post.getDescription())
                        .image(ImageUtils.decompressImage(post.getImage()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id) {
        Optional<Post> post = repository.findById(id);

        if(post.isPresent()){

            PostDTO postDTO = PostDTO.builder()
                    .title(post.get().getTitle())
                    .description(post.get().getDescription())
                    .image(ImageUtils.decompressImage(post.get().getImage()))
                    .build();
            return postDTO;

        } else {
            throw new NotFoundException("Post with id " + id + " not found!");
        }
    }

    @Override
    public PostDTO createPost(PostDTO postDTO, MultipartFile file) throws IOException {

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .description(postDTO.getDescription())
                .image(ImageUtils.compressImage(file.getBytes()))
                .build();

        Post savedPost = repository.save(post);
        PostDTO savedPostDTO = PostMapper.mapPostToDTO(savedPost);

        return savedPostDTO;
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO postDTO, MultipartFile file) throws IOException {
        Post existingPost = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post with id " + id + " not found!"));

        existingPost.setTitle(postDTO.getTitle());
        existingPost.setDescription(postDTO.getDescription());
        existingPost.setImage(ImageUtils.compressImage(file.getBytes()));
        Post updatedPost = repository.save(existingPost);

        return PostMapper.mapPostToDTO(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post with id " + id + " not found!"));

        repository.delete(post);
    }
}
