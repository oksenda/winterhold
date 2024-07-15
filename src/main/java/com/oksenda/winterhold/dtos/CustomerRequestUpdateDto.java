package com.oksenda.winterhold.dtos;

import com.oksenda.winterhold.models.Gender;
import com.oksenda.winterhold.validators.MembershipNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Data
public class CustomerRequestUpdateDto {
    private  String membershipNumber;
    @NotNull
    @NotBlank
    private final String firstName;
    private final String lastName;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;
    @NotNull
    private final Gender gender;
    private final String phone;
    private final String address;
}
