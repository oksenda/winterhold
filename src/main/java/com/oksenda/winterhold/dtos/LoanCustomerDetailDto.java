package com.oksenda.winterhold.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class LoanCustomerDetailDto {
    private final String membershipNumber;
    private final String fullName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate membershipExpireDate;
    private final String phone;
}
