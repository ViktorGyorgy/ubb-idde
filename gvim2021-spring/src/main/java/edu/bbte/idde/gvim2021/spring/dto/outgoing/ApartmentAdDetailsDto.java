package edu.bbte.idde.gvim2021.spring.dto.outgoing;

import lombok.Value;

@Value
public class ApartmentAdDetailsDto {
    private Long id;
    private Integer numberOfRooms;
    private Long price;
    private String location;
    private String description;
    private Integer numberOfBathrooms;
}
