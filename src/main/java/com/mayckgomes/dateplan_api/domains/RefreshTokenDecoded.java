package com.mayckgomes.dateplan_api.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RefreshTokenDecoded {

    private String jwtid;
    private Long userId;

}
