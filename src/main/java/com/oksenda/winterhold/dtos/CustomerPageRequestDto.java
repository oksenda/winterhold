package com.oksenda.winterhold.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerPageRequestDto {
    private final Integer pageNumber;
    private final String name;
    private final String membershipNumber;
}
