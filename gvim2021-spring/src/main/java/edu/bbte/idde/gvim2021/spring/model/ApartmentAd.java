package edu.bbte.idde.gvim2021.spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "apartmentads")
public class ApartmentAd extends BaseEntity {
    private Integer numberOfRooms;
    private Long price;
    private String location;
    private String description;
    private Integer numberOfBathrooms;

    @OneToMany(mappedBy = "apartmentAd", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

}
