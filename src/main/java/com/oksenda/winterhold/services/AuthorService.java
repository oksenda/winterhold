package com.oksenda.winterhold.services;

import com.oksenda.winterhold.dtos.AuthorItemDto;
import com.oksenda.winterhold.dtos.AuthorPageRequestDto;
import com.oksenda.winterhold.dtos.BookItemDto;
import com.oksenda.winterhold.dtos.UpsertAuthorRequestDto;
import org.springframework.data.domain.Page;

import java.math.BigInteger;
import java.util.List;

public interface AuthorService {
    Page<AuthorItemDto> getAuthor(AuthorPageRequestDto dto);

    void insert(UpsertAuthorRequestDto dto);
    void update(UpsertAuthorRequestDto dto);
    UpsertAuthorRequestDto getAuthor(BigInteger id);
    Boolean delete(BigInteger id);
    List<AuthorItemDto> getAutorsList();

    List<BookItemDto> getBooks(BigInteger id);
}
