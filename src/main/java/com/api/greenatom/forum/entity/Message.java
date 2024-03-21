package com.api.greenatom.forum.entity;

import com.api.greenatom.forum.dto.request.MessageRequestDTO;
import com.api.greenatom.forum.dto.response.MessageResponseDTO;
import com.api.greenatom.security.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)

    private User user;

    @Column(name = "creation_date",nullable = false)

    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "topic_id")

    private Long topicId;



    /**
     * Создает новый экземпляр MessageResponseDTO на основе Message.
     *
     * @param message Сообщение для DTO.
     * @return Новый экземпляр MessageResponseDTO.
     */
    public static MessageResponseDTO messageToDTO(Message message) {
        return new MessageResponseDTO(
                message.getId(),
                message.getText(),
                message.getUser().getUsername(),
                message.getCreationDate()
        );
    }


    /**
     * Создает новый экземпляр Message на основе DTO и пользователя.
     *
     * @param requestDTO DTO для сообщения.
     * @param user Пользователь, отправивший сообщение.
     * @param topicId Идентификатор топика, к которому принадлежит сообщение.
     * @return Новый экземпляр Message.
     */
    public static Message messageFromDTO(MessageRequestDTO requestDTO, User user, Long topicId) {
        Message message = new Message();
        message.setText(requestDTO.getText());
        message.setUser(user);
        message.setTopicId(topicId);
        message.setCreationDate(LocalDateTime.now());
        return message;
    }
}
