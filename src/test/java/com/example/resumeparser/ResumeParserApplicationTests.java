package com.example.resumeparser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
class ResumeParserApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public static void main(String[] args) {
        Flux<String> responseFlux = Flux.just(
                "event:conversation.chat.created",
                "data:{\"id\":\"7382159487131697202\",\"conversation_id\":\"7381473525342978089\",\"bot_id\":\"7379462189365198898\",\"completed_at\":1718792949,\"last_error\":{\"code\":0,\"msg\":\"\"},\"status\":\"created\",\"usage\":{\"token_count\":0,\"output_count\":0,\"input_count\":0}}",
                "event:conversation.message.completed",
                "data:{\"id\":\"7382159494123470858\",\"conversation_id\":\"7381473525342978089\",\"bot_id\":\"7379462189365198898\",\"role\":\"assistant\",\"type\":\"answer\",\"content\":\"2024 年 10 月 1 日是星期三。\",\"content_type\":\"text\",\"chat_id\":\"7382159487131697202\"}",
                "event:conversation.chat.completed",
                "data:{\"id\":\"7382159487131697202\",\"conversation_id\":\"7381473525342978089\",\"bot_id\":\"7379462189365198898\",\"completed_at\":1718792949,\"last_error\":{\"code\":0,\"msg\":\"\"},\"status\":\"completed\",\"usage\":{\"token_count\":633,\"output_count\":19,\"input_count\":614}}",
                "event:done",
                "data:\"[DONE]\""
        );

        responseFlux
                .skipUntil(line -> line.startsWith("event:conversation.message.completed"))
                .take(2)
                .filter(line -> line.startsWith("data:"))
                .map(line -> line.substring("data:".length()).trim())
                .map(jsonData -> {
                    try {
                        return new ObjectMapper().readValue(jsonData, Map.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(map -> (String) map.get("content"))
                .subscribe(content -> {
                    System.out.println("Content: " + content);
                });
    }

}
