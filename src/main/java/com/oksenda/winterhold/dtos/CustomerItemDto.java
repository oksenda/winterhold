package com.oksenda.winterhold.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Data
public class CustomerItemDto {
    private final String membershipNumber;
    private final String fullName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate membershipExpireDate;
}
