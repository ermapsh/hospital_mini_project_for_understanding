package com.ermapsh.hospital.controller;

import com.ermapsh.hospital.dto.PatchTitleDTO;
import com.ermapsh.hospital.dto.RestDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;


@RestController
@RequestMapping(path= "rest")
@RequiredArgsConstructor
public class RestClientUsageController {

    private final RestClient restClient;

    @GetMapping("")
    public List<RestDataDTO> getApi(){
        return restClient.get().
                 retrieve().
                 body(new ParameterizedTypeReference<>() {
                 });
    }

    @PostMapping("")
    public RestDataDTO postAPi(@RequestBody RestDataDTO restDataDTO){
        return restClient.post().
                body(restDataDTO).
                retrieve().
                body(RestDataDTO.class);
    }

    @PutMapping("/{postId}")
    public RestDataDTO putAPi(@RequestBody RestDataDTO restDataDTO, @PathVariable Long postId){
        return restClient.put().
                uri("/{postId}",postId).
                body(restDataDTO).
                retrieve().
                body(RestDataDTO.class);
    }

    @PatchMapping("/{postId}")
    public RestDataDTO patchAPi(@RequestBody PatchTitleDTO body, @PathVariable Long postId){
        return restClient.patch().
                uri("/{postId}",postId).
                body(body).
                retrieve().
                body(RestDataDTO.class);
    }

    @DeleteMapping("/{postId}")
    public String deleteApi(@PathVariable Long postId) {

        restClient.delete()
                .uri("/{postId}", postId)
                .retrieve()
                .toBodilessEntity();

        return "Deleted successfully";
    }
}
