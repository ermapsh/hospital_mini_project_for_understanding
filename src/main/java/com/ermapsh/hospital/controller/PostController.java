package com.ermapsh.hospital.controller;

import com.ermapsh.hospital.dto.PostDTO;
import com.ermapsh.hospital.entity.PostEntity;
import com.ermapsh.hospital.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( path= "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public List<PostEntity> get(){
        return postService.getAll();
    }

    @GetMapping("/{postId}")
    public PostEntity getById(@PathVariable Long postId){
        return postService.getById(postId);
    }

    @PostMapping("")
    public PostDTO createNewPost(@RequestBody PostDTO inputPost){
        return postService.createNewPost(inputPost);
    }

    @PutMapping("/{postId}")
    public PostEntity updatePost(@PathVariable Long postId, @RequestBody PostDTO postDTO){
        return postService.updatePostById(postId, postDTO);
    }
}
