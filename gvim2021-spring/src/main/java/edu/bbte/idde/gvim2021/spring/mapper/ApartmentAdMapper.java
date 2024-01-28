package edu.bbte.idde.gvim2021.spring.mapper;

import edu.bbte.idde.gvim2021.spring.dto.incoming.ApartmentAdCreateDto;
import edu.bbte.idde.gvim2021.spring.dto.outgoing.ApartmentAdDetailsDto;
import edu.bbte.idde.gvim2021.spring.model.ApartmentAd;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public abstract class ApartmentAdMapper {

    @IterableMapping(elementTargetType = ApartmentAdDetailsDto.class)
    public abstract Collection<ApartmentAdDetailsDto> modelsToDetailDtos(Iterable<ApartmentAd> model);

    public abstract ApartmentAdDetailsDto modelToDetailsDto(ApartmentAd model);

    public abstract ApartmentAd createDtoToModel(ApartmentAdCreateDto createDao);

}
