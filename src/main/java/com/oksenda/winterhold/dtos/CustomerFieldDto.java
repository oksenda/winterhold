package com.oksenda.winterhold.dtos;

import com.oksenda.winterhold.models.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Builder
@Data
public class CustomerFieldDto {
    private final String membershipNumber;
    private final String fullName;
    private final String firstName;
    private final String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;
    private final Gender gender;
    private final String phone;
    private final String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate membershipExpireDate;
}
