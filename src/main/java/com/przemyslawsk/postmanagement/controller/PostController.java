package com.przemyslawsk.postmanagement.controller;

import com.przemyslawsk.postmanagement.dto.PostDTO;
import com.przemyslawsk.postmanagement.exception.NotFoundException;
import com.przemyslawsk.postmanagement.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = service.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") Long postId) {
        try {
            PostDTO post = service.getPostById(postId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO post,
                                              @RequestParam("img") MultipartFile file) throws IOException {
        PostDTO savedPost = service.createPost(post, file);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable("id") Long postId,
                                              @RequestBody PostDTO post,
                                              @RequestParam("img") MultipartFile file) throws IOException {
        try {
            PostDTO updatedPost = service.updatePost(postId, post, file);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long postId) {
        try {
            service.deletePost(postId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
