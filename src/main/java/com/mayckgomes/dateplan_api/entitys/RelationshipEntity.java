package com.mayckgomes.dateplan_api.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "relationship")
@Table(name = "relationship")
@Getter
@Setter
@NoArgsConstructor
public class RelationshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId1;
    private Long userId2;
    private String initialDay;

}
