package com.api.greenatom.forum.dto.request;


import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String message;
}
