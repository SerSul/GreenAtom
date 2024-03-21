package com.api.greenatom.forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
public class TopicResponseDTO {
    private String title;

    List<MessageResponseDTO> messages;
}
