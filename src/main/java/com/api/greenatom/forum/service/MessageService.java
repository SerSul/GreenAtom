package com.api.greenatom.forum.service;

import com.api.greenatom.forum.dto.response.MessageResponseDTO;
import com.api.greenatom.forum.entity.Message;

import java.util.List;

public interface MessageService {
    Message createMessage(Long topicId, Message message);
    Message updateMessage(Long id, Message messageDetails);
    void deleteMessage(Long id);
    Message getMessageById(Long id);
    List<Message> getMessagesByTopicId(Long topicId);

}
