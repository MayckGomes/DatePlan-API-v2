package com.mayckgomes.dateplan_api.dto.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    @Size(max = 50)
    @NotNull
    @NotBlank
    private String name;

    @Email
    @Size(max = 255)
    @NotNull
    @NotBlank
    private String email;

    @Size(max = 250)
    @NotNull
    @NotBlank
    private String password;

    @NotNull
    private Long policyPrivacyAcceptedVersion;

    @Size(max = 10)
    @NotNull
    @NotBlank
    private String policyPrivacyAcceptedAt;

    @NotNull
    private Long TermsOfUseAcceptedVersion;

    @Size(max = 10)
    @NotNull
    @NotBlank
    private String TermsOfUseAcceptedAt;

}
