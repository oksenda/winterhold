package com.oksenda.winterhold.services;

import com.oksenda.winterhold.dtos.*;
import org.springframework.data.domain.Page;

import java.math.BigInteger;
import java.util.List;

public interface BookService {
    Page<BookItemDto> getBooks(BookPageRequestDto dto);

    void insert(InsertRequestBookDto dto);
    void update(UpdateBookRequestDto dto);
    UpdateBookRequestDto getBook(String code);
    Boolean delete(String code);

    boolean isExist(String code);

}
