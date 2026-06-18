package com.ermapsh.hospital.service;

import com.ermapsh.hospital.dto.PostDTO;
import com.ermapsh.hospital.entity.PostEntity;
import com.ermapsh.hospital.repository.PostEntityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final ModelMapper modelMapper;

    public List<PostEntity> getAll(){
        return postEntityRepository.findAll();
    }

    public PostEntity getById(Long postId){
        return postEntityRepository.findById(postId).orElseThrow();
    }

    public PostDTO createNewPost(PostDTO inputPost){
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        PostEntity createdPost = postEntityRepository.save(postEntity);
        return modelMapper.map(createdPost, PostDTO.class);
    }

    public PostEntity updatePostById(Long postId, PostDTO inputPost) {

        PostEntity postEntity = postEntityRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        postEntity.setTitle(inputPost.getTitle());
        postEntity.setDescription(inputPost.getDescription());

        return postEntityRepository.save(postEntity);
    }
}
