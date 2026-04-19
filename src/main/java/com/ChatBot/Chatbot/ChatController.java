package com.ChatBot.Chatbot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private OllamaService ollamaService;

//    @PostMapping("/chat")
//    public String chat(@RequestBody ChatModel request) {
//        return ollamaService.getResponse(request.getMessage());
//    }
@PostMapping("/chat")
public Map<String, String> chat(@RequestBody Map<String, String> request) throws Exception {
    return ollamaService.getResponse(request.get("message"));
}
}
