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

    /**
     * Конструктор для сервиса сообщений.
     *
     * @param messageRepository репозиторий для работы с сообщениями
     * @param topicRepository репозиторий для работы с топиками
     * @param userService сервис для работы с пользователями
     */
    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, TopicRepository topicRepository, UserServiceImpl userService) {
        this.messageRepository = messageRepository;
        this.topicRepository = topicRepository;
        this.userService = userService;
    }

    /**
     * Создает и сохраняет новое сообщение в указанном топике.
     *
     * @param topicId идентификатор топика
     * @param messageDTO DTO сообщения
     * @return DTO созданного сообщения
     */
    @Override
    public MessageResponseDTO createMessage(Long topicId, MessageRequestDTO messageDTO) {
        User user = userService.getCurrentUser();
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found for this id :: " + topicId));

        Message message = Message.messageFromDTO(messageDTO, user, topicId);

        Message savedMessage = messageRepository.save(message);

        return Message.messageToDTO(savedMessage);
    }

    /**
     * Обновляет текст сообщения.
     *
     * @param messageId идентификатор сообщения для обновления
     * @param messageDTO DTO с новым текстом сообщения
     * @return DTO обновленного сообщения
     * @throws AccessDeniedException если текущий пользователь не автор сообщения
     */
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

    /**
     * Удаляет сообщение по его идентификатору.
     *
     * @param messageId идентификатор сообщения для удаления
     * @throws AccessDeniedException если текущий пользователь не автор сообщения
     */
    @Override
    public void deleteMessage(Long messageId) throws AccessDeniedException {
        User user = userService.getCurrentUser();
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found for this id :: " + messageId));

        if (!message.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("User not authorized to delete this message");
        }

        messageRepository.delete(message);
    }

    /**
     * Возвращает сообщение по его идентификатору.
     *
     * @param id идентификатор сообщения
     * @return сообщение
     */
    @Override
    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found for this id :: " + id));
    }
    /**
     * Возвращает все сообщения в указанном топике.
     *
     * @param topicId идентификатор топика
     * @return список сообщений в топике
     */
    @Override
    public List<Message> getMessagesByTopicId(Long topicId) {
        return messageRepository.findAllByTopicId(topicId);
    }



}

