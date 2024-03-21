package com.api.greenatom.forum;

import com.api.greenatom.forum.dto.request.TopicRequestDTO;
import com.api.greenatom.forum.dto.response.TopicResponseDTO;
import com.api.greenatom.forum.entity.Topic;
import com.api.greenatom.forum.service.TopicService;
import com.api.greenatom.forum.service.impl.TopicServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicService topicService;
    @Autowired
    public TopicController(TopicServiceImpl topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/getAllTopics")
    public List<TopicResponseDTO> getAllTopics() {
        return topicService.getAllTopics();
    }

    @PostMapping("/createTopic")
    public TopicResponseDTO createTopic(@RequestBody @Valid TopicRequestDTO topic) {
        return topicService.createTopic(topic);
    }

}

