package edu.bbte.idde.gvim2021.apartmentad.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Comment extends BaseEntity {
    String author;
    String text;
    Long apartmentAdId;
}
