package edu.bbte.idde.gvim2021.spring.mapper;

import edu.bbte.idde.gvim2021.spring.dto.incoming.CommentCreateDto;
import edu.bbte.idde.gvim2021.spring.dto.outgoing.CommentDetailsDto;
import edu.bbte.idde.gvim2021.spring.model.Comment;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public abstract class CommentMapper {
    @IterableMapping(elementTargetType = CommentDetailsDto.class)
    public abstract Collection<CommentDetailsDto> modelsToDetailDtos(Iterable<Comment> model);

    public abstract CommentDetailsDto modelToDetailsDto(Comment model);

    public abstract Comment createDtoToModel(CommentCreateDto createDao);
}
