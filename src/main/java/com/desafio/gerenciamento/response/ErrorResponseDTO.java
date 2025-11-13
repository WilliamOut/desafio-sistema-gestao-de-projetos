package com.desafio.gerenciamento.response;

import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
    int status,
    LocalDateTime timestamp,
    String error,
    String message,
    String path,
    List<String> details
)
{
}
