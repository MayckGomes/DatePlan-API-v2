package com.mayckgomes.dateplan_api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserResponse {

    private Long id;
    private String publicId;
    private String name;
    private Long relationshipId;
    private String plan;
    private Long acceptPolicyPrivacyVersion;
    private Long acceptTermsOfUseVersion;


}
