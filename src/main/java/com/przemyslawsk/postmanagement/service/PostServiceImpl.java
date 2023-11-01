package com.przemyslawsk.postmanagement.service;

import com.przemyslawsk.postmanagement.dto.PostDTO;
import com.przemyslawsk.postmanagement.dto.PostMapper;
import com.przemyslawsk.postmanagement.exception.NotFoundException;
import com.przemyslawsk.postmanagement.model.Post;
import com.przemyslawsk.postmanagement.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .map(PostMapper::mapPostToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id) {
        Optional<Post> post = repository.findById(id);

        if(post.isPresent()){
            PostDTO postDTO = PostMapper
                    .mapPostToDTO(post.get());
            return postDTO;
        } else {
            throw new NotFoundException("Post with id " + id + " not found!");
        }
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = PostMapper.mapToPost(postDTO);
        Post savedPost = repository.save(post);
        PostDTO savedPostDTO = PostMapper.mapPostToDTO(savedPost);

        return savedPostDTO;
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post existingPost = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post with id " + id + " not found!"));

        existingPost.setTitle(postDTO.getTitle());
        existingPost.setDescription(postDTO.getDescription());
        existingPost.setImage(postDTO.getImage());
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
