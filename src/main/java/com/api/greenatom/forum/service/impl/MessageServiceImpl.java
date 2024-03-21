package com.api.greenatom.forum.service.impl;

import com.api.greenatom.forum.dto.request.MessageRequestDTO;
import com.api.greenatom.forum.dto.response.MessageResponseDTO;
import com.api.greenatom.forum.entity.Message;
import com.api.greenatom.forum.entity.Topic;
import com.api.greenatom.forum.repository.MessageRepository;
import com.api.greenatom.forum.repository.TopicRepository;
import com.api.greenatom.forum.service.MessageService;
import com.api.greenatom.security.entity.User;
import com.api.greenatom.security.service.UserService;
import com.api.greenatom.security.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final TopicRepository topicRepository;
    private final UserService userService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, TopicRepository topicRepository, UserServiceImpl userService) {
        this.messageRepository = messageRepository;
        this.topicRepository = topicRepository;
        this.userService = userService;
    }

    @Override
    public MessageResponseDTO createMessage(Long topicId, MessageRequestDTO messageDTO) {
        User user = userService.getCurrentUser();
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found for this id :: " + topicId));

        Message message = Message.messageFromDTO(messageDTO, user, topicId);

        Message savedMessage = messageRepository.save(message);

        return Message.messageToDTO(savedMessage);
    }

    @Override
    public MessageResponseDTO updateMessage(Long messageId, MessageRequestDTO messageDTO) throws AccessDeniedException {
        User user = userService.getCurrentUser();
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found for this id :: " + messageId));

        if (!message.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("User not authorized to update this message");
        }

        message.setText(messageDTO.getText());
        Message updatedMessage = messageRepository.save(message);

        return Message.messageToDTO(updatedMessage);
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



}

