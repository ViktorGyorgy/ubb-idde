package edu.bbte.idde.gvim2021.apartmentad.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ApartmentAd extends BaseEntity {
    private Integer numberOfRooms;
    private Long price;
    private String location;
    private String description;
    private Integer numberOfBathrooms;
}
