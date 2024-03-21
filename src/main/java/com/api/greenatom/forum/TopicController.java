package com.api.greenatom.forum;

import com.api.greenatom.forum.dto.request.MessageRequestDTO;
import com.api.greenatom.forum.dto.request.TopicRequestDTO;
import com.api.greenatom.forum.dto.response.MessageResponseDTO;
import com.api.greenatom.forum.dto.response.TopicResponseDTO;
import com.api.greenatom.forum.entity.Message;
import com.api.greenatom.forum.exception.TopicAlreadyExistsException;
import com.api.greenatom.forum.service.MessageService;
import com.api.greenatom.forum.service.TopicService;
import com.api.greenatom.forum.service.impl.MessageServiceImpl;
import com.api.greenatom.forum.service.impl.TopicServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
public class TopicController {
    private final TopicService topicService;
    private final MessageService messageService;

    /**
     * Конструктор для TopicController.
     *
     * @param topicService Сервис для работы с топиками.
     * @param messageService Сервис для работы с сообщениями.
     */
    @Autowired
    public TopicController(TopicServiceImpl topicService, MessageServiceImpl messageService) {
        this.topicService = topicService;
        this.messageService = messageService;
    }

    /**
     * Получение списка всех топиков.
     *
     * @return Список DTO топиков.
     */
    @GetMapping("topics/getAllTopics")
    public List<TopicResponseDTO> getAllTopics() {
        return topicService.getAllTopics();
    }

    /**
     * Создание нового топика.
     *
     * @param topic DTO запроса для создания топика.
     * @return DTO созданного топика.
     * @throws TopicAlreadyExistsException если топик с таким названием уже существует.
     */
    @PostMapping("topics/createTopic")
    public TopicResponseDTO createTopic(@RequestBody @Valid TopicRequestDTO topic) {
        return topicService.createTopic(topic);
    }

    /**
     * Получение списка сообщений по идентификатору топика.
     *
     * @param topicId Идентификатор топика.
     * @return Ответ с списком DTO сообщений.
     */
    @GetMapping("messages/{topicId}/getMessages")
    public ResponseEntity<List<MessageResponseDTO>> getMessagesByTopicId(@PathVariable Long topicId) {
        List<Message> messages = messageService.getMessagesByTopicId(topicId);
        List<MessageResponseDTO> messageResponseDTOS = messages.stream()
                .map(Message::messageToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(messageResponseDTOS);
    }

    /**
     * Создание нового сообщения в топике.
     *
     * @param topicId Идентификатор топика.
     * @param requestDTO DTO запроса для создания сообщения.
     * @return Ответ с DTO созданного сообщения.
     */
    @PostMapping("messages/{topicId}/createMessages")
    public ResponseEntity<MessageResponseDTO> createMessage(@PathVariable Long topicId, @RequestBody @Valid MessageRequestDTO requestDTO) {
        MessageResponseDTO createdMessage = messageService.createMessage(topicId, requestDTO);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    /**
     * Обновление сообщения.
     *
     * @param messageId Идентификатор сообщения для обновления.
     * @param messageDTO DTO запроса для обновления сообщения.
     * @return Ответ с DTO обновленного сообщения.
     * @throws AccessDeniedException если у пользователя нет доступа к этому действию.
     */
    @PutMapping("messages/{messageId}/update")
    public ResponseEntity<MessageResponseDTO> updateMessage(@PathVariable Long messageId, @RequestBody @Valid MessageRequestDTO messageDTO) throws AccessDeniedException {
        MessageResponseDTO updatedMessage = messageService.updateMessage(messageId, messageDTO);
        return ResponseEntity.ok(updatedMessage);
    }

    /**
     * Удаление сообщения.
     *
     * @param messageId Идентификатор сообщения для удаления.
     * @return Ответ с подтверждением удаления.
     * @throws AccessDeniedException если у пользователя нет доступа к этому действию.
     */
    @DeleteMapping("/messages/{messageId}/delete")
    public ResponseEntity<Map<String, String>> deleteMessage(@PathVariable Long messageId) throws AccessDeniedException {
        messageService.deleteMessage(messageId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Message with ID " + messageId + " has been deleted.");
        return ResponseEntity.ok(response);
    }




}

