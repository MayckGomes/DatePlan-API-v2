package com.mayckgomes.dateplan_api.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "relationships")
@Table(name = "relationships")
@Getter
@Setter
@NoArgsConstructor
public class RelationshipsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id1")
    private Long userId1;

    @Column(name = "user_id2")
    private Long userId2;

    @Column(name = "initial_day")
    private String initialDay;

}
