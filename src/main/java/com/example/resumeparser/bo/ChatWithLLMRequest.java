package com.example.resumeparser.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 * @title ChatWithLLMRequest
 * @description
 * @author haoyu99
 * @version 1.0.0
 * @create 2024/8/3 22:36
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatWithLLMRequest {
    private String model;
    private List<Message> messages;
    private boolean stream;
    private int max_tokens;
    private double temperature;
    private double top_p;
    private int top_k;
    private double frequency_penalty;
    private int n;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
}
