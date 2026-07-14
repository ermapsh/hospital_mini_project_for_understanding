package com.ermapsh.hospital.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AIService {

    private final ChatClient chatClient;

    public String getJoke(String topic){
        return  chatClient.prompt()
                .user("Give me joke on the topic: "+ topic)
                .call()
                .content();
    }
}
