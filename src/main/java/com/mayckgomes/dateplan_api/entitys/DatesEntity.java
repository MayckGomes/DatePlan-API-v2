package com.mayckgomes.dateplan_api.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "dates")
@Table(name = "dates")
@NoArgsConstructor
@Getter
@Setter
public class DatesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String time;
    private String local;
    private String title;
    private String description;

    @Column(name = "id_icon")
    private Long iconId;

    @Column(name = "id_relationship")
    private Long relationshipId;

    @Column(name = "id_author")
    private Long authorId;

}
