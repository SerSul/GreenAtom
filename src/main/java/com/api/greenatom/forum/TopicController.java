package com.api.greenatom.forum;

import com.api.greenatom.forum.dto.request.TopicRequestDTO;
import com.api.greenatom.forum.dto.response.MessageResponseDTO;
import com.api.greenatom.forum.dto.response.TopicResponseDTO;
import com.api.greenatom.forum.entity.Message;
import com.api.greenatom.forum.service.MessageService;
import com.api.greenatom.forum.service.TopicService;
import com.api.greenatom.forum.service.impl.MessageServiceImpl;
import com.api.greenatom.forum.service.impl.TopicServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicService topicService;
    private final MessageService messageService;
    @Autowired
    public TopicController(TopicServiceImpl topicService, MessageServiceImpl messageService) {
        this.topicService = topicService;
        this.messageService = messageService;
    }

    @GetMapping("/getAllTopics")
    public List<TopicResponseDTO> getAllTopics() {
        return topicService.getAllTopics();
    }

    @PostMapping("/createTopic")
    public TopicResponseDTO createTopic(@RequestBody @Valid TopicRequestDTO topic) {
        return topicService.createTopic(topic);
    }

    @GetMapping("/{topicId}/messages")
    public ResponseEntity<List<MessageResponseDTO>> getMessagesByTopicId(@PathVariable Long topicId) {
        List<Message> messages = messageService.getMessagesByTopicId(topicId);
        List<MessageResponseDTO> messageResponseDTOS = messages.stream()
                .map(Message::messageToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(messageResponseDTOS);
    }

}

