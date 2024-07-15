package com.oksenda.winterhold.services;

import com.oksenda.winterhold.dtos.*;
import org.springframework.data.domain.Page;

import java.math.BigInteger;
import java.util.List;

public interface LoanService {
    Page<LoanItemDto> getLoans(LoanPageRequeatDto dto);

    LoanFieldDto getLoan(BigInteger id);

    void insert(LoanUpsertRequestDto dto);
    void update(LoanUpsertRequestDto dto);
    void returnLoan(BigInteger id);
    List<BookItemDto> getBookList();
    List<CustomerItemDto> getCustomerList();
    LoanBookDetailDto getBookDetail(BigInteger id);
    LoanCustomerDetailDto getCustomerDetail(BigInteger id);
}
