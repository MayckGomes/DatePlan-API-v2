package com.mayckgomes.dateplan_api.entitys;


import com.mayckgomes.dateplan_api.dto.date.DateResponse;
import com.mayckgomes.dateplan_api.dto.memories.MemoryResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "memories")
@Table(name = "memories")
@NoArgsConstructor
@Getter
@Setter
public class MemoriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String local;
    private String title;
    private String description;

    @Column(name = "id_icon")
    private Long iconId;

    @Column(name = "id_relationship")
    private Long relationshipId;

    @Column(name = "id_author")
    private Long authorId;

    public MemoryResponse toMemoryResponse(){

        return new MemoryResponse(
                id,
                date,
                local,
                title,
                description,
                iconId,
                relationshipId,
                authorId
        );

    }

}
