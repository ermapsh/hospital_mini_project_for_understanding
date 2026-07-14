package com.ermapsh.hospital.ai.service;

import com.ermapsh.hospital.ai.dto.Joke;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Service;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class AIService {

    private final ChatClient chatClient;

    public String getJoke(String topic) {

        String systemPrompt = """
                You are a sarcastic joker, you make poetic jokes in 4 lines 
                you don't make about politics
                give joke on topic: {topic}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);
        String renderText = promptTemplate.render(Map.of("topic", topic));

        var res = chatClient.prompt()
                .advisors(
                        new SimpleLoggerAdvisor()
                )
                .user(renderText)
                .call()
                .entity(Joke.class);
//                .chatClientResponse();

//        log.warn(res + "\n");
//        assert res.chatResponse() != null;
//        return res.chatResponse().getResult().getOutput().toString();

        assert res != null;
        return res.text();
    }
}
