package edu.bbte.idde.gvim2021.spring.dto.incoming;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class ApartmentAdCreateDto {
    @Positive
    private Integer numberOfRooms;

    @Positive
    private Long price;

    @Size(max = 1024)
    private String location;

    @Size(max = 8192)
    private String description;

    @Positive
    private Integer numberOfBathrooms;
}
