package com.mayckgomes.dateplan_api.dto.date;

import com.mayckgomes.dateplan_api.entitys.DatesEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EditDateRequest {

    @NotNull
    private Long id;

    @NotNull
    @NotBlank
    private String date;

    @NotNull
    @NotBlank
    private String time;

    @NotNull
    @NotBlank
    private String local;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    private String description;

    @NotNull
    private Long iconId;

    @NotNull
    private Long relationshipId;

    @NotNull
    private Long authorId;

    public DatesEntity toDatesEntity(){

        var entity = new DatesEntity();

        entity.setId(id);
        entity.setDate(date);
        entity.setTime(time);
        entity.setLocal(local);
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setIconId(iconId);
        entity.setRelationshipId(relationshipId);
        entity.setAuthorId(authorId);

        return entity;

    }

}
