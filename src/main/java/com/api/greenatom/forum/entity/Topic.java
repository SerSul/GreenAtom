package com.api.greenatom.forum.entity;

import com.api.greenatom.forum.dto.response.MessageResponseDTO;
import com.api.greenatom.forum.dto.response.TopicResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    public Topic(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "topic_id")
    private List<Message> messages = new ArrayList<>();

    public TopicResponseDTO toTopicResponseDTO() {
        List<MessageResponseDTO> messageResponseDTOs = this.messages.stream()
                .map(message -> new MessageResponseDTO(message.getId(),message.getText(), message.getUser().getUsername(), message.getCreationDate()))
                .collect(Collectors.toList());
        return new TopicResponseDTO(this.id ,this.title, messageResponseDTOs);
    }



}