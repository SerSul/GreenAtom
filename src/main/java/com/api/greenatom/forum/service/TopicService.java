package com.api.greenatom.forum.service;

import com.api.greenatom.forum.dto.request.TopicRequestDTO;
import com.api.greenatom.forum.dto.response.TopicResponseDTO;
import com.api.greenatom.forum.entity.Topic;

import java.util.List;

public interface TopicService {
    TopicResponseDTO createTopic(TopicRequestDTO topic);
    Topic updateTopic(Long id, Topic topicDetails);
    void deleteTopic(Long id);
    Topic getTopicById(Long id);
    List<TopicResponseDTO> getAllTopics();
}

