package com.api.greenatom.forum.service.impl;

import com.api.greenatom.forum.dto.response.MessageResponseDTO;
import com.api.greenatom.forum.entity.Message;
import com.api.greenatom.forum.repository.MessageRepository;
import com.api.greenatom.forum.repository.TopicRepository;
import com.api.greenatom.forum.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, TopicRepository topicRepository) {
        this.messageRepository = messageRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public Message createMessage(Long topicId, Message message) {
        return topicRepository.findById(topicId).map(topic -> {
            return messageRepository.save(message);
        }).orElseThrow(() -> new ResourceNotFoundException("Topic not found for this id :: " + topicId));
    }

    @Override
    public Message updateMessage(Long id, Message messageDetails) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found for this id :: " + id));
        message.setText(messageDetails.getText());
        // Обновите другие поля при необходимости
        return messageRepository.save(message);
    }

    @Override
    public void deleteMessage(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found for this id :: " + id));
        messageRepository.delete(message);
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found for this id :: " + id));
    }

    @Override
    public List<Message> getMessagesByTopicId(Long topicId) {
        return messageRepository.findAllByTopicId(topicId);
    }


    public static MessageResponseDTO messageToDTO(Message message) {
        return new MessageResponseDTO(
                message.getText(),
                message.getUser().getUsername(),
                message.getCreationDate(),
                message.getTopicId()
        );
    }
}

