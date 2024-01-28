package edu.bbte.idde.gvim2021.spring.dto.incoming;

import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class CommentCreateDto {
    @Size(max = 1024)
    String author;
    @Size(max = 1024)
    String text;
}
