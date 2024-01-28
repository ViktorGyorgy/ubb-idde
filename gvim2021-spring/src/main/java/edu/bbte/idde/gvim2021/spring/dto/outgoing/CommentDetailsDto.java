package edu.bbte.idde.gvim2021.spring.dto.outgoing;

import lombok.Value;

@Value
public class CommentDetailsDto {
    private String author;
    private String text;
    private Long id;
}
