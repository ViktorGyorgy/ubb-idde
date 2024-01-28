package edu.bbte.idde.gvim2021.spring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
    String author;
    String text;

    @ToString.Exclude
    @ManyToOne(optional = false)
    private ApartmentAd apartmentAd;
}
