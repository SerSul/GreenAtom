package com.api.greenatom.forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageResponseDTO {
    private Long id;
    private String text;
    private String authorName;
    private LocalDateTime creationDate;
}
