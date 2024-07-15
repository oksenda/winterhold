package com.oksenda.winterhold.services;

import com.oksenda.winterhold.dtos.*;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Page<CustomerItemDto> getCustomers(CustomerPageRequestDto dto);
    Boolean isExist(String membershipNumber);

    CustomerFieldDto getCustomer(String membershipNumber);

    void insert(CustomerRequestInsertDto dto);
    void update(CustomerRequestUpdateDto dto);
    Boolean isDelete(String membershipNumber);
    void updateExpireDate(String membershipNumber);
}
