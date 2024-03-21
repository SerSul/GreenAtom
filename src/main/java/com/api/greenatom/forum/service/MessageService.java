package com.api.greenatom.forum.service;

import com.api.greenatom.forum.dto.request.MessageRequestDTO;
import com.api.greenatom.forum.dto.response.MessageResponseDTO;
import com.api.greenatom.forum.entity.Message;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface MessageService {
    MessageResponseDTO createMessage(Long topicId, MessageRequestDTO message);
    MessageResponseDTO updateMessage(Long id, MessageRequestDTO messageDetails) throws AccessDeniedException;
    void deleteMessage(Long id);
    Message getMessageById(Long id);
    List<Message> getMessagesByTopicId(Long topicId);

}
