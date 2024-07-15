package com.oksenda.winterhold.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.time.LocalDate;
@Data
@Builder
public class UpsertAuthorRequestDto {
    private BigInteger id;
    @NotNull
    @NotBlank
    private final String firstName;
    private final String lastName;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;
    @NotNull
    @NotBlank
    private final String title;
    private final String education;
    private final String summary;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate deceasedDate;
    private final String fullName;

}
