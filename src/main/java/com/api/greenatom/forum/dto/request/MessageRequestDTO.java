package com.api.greenatom.forum.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageRequestDTO {
    @NotBlank
    private String text;
}
