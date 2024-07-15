package com.oksenda.winterhold.services.impls;

import com.oksenda.winterhold.dtos.AuthorItemDto;
import com.oksenda.winterhold.dtos.AuthorPageRequestDto;
import com.oksenda.winterhold.dtos.BookItemDto;
import com.oksenda.winterhold.dtos.UpsertAuthorRequestDto;
import com.oksenda.winterhold.models.Author;
import com.oksenda.winterhold.repositories.AuthorRepository;
import com.oksenda.winterhold.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Page<AuthorItemDto> getAuthor(AuthorPageRequestDto dto) {
        Pageable pageable = PageRequest.of(dto.getPageNumber() != null ? dto.getPageNumber() - 1 : 0, 10);
        var authorsPage = authorRepository.findSearch(pageable, dto.getName());

        List<AuthorItemDto> authorItemList = new ArrayList<>();
        authorsPage.forEach(author -> {
            AuthorItemDto authorItemDto = AuthorItemDto.builder()
                    .id(author.getId())
                    .education(author.getEducation())
                    .fullName(author.getFullName())
                    .isLife(author.getDeceasedDate()==null?"Alive":"Deceased")
                    .age(author.getAge())
                    .build();
            authorItemList.add(authorItemDto);
        });

        return new PageImpl<>(authorItemList, authorsPage.getPageable(), authorsPage.getTotalElements());
    }

    @Override
    public void insert(UpsertAuthorRequestDto dto) {
        var author = Author.builder()
                .firstName(dto.getFirstName())
                .birthDate(dto.getBirthDate())
                .title(dto.getTitle())
                .summary(dto.getSummary())
                .lastName(dto.getLastName())
                .education(dto.getEducation())
                .deceasedDate(dto.getDeceasedDate())
                .build();
        authorRepository.save(author);
    }

    @Override
    public void update(UpsertAuthorRequestDto dto) {
        var author = authorRepository.findById(dto.getId()).orElseThrow(()->new IllegalArgumentException("Author Not found"));
        author.setBirthDate(dto.getBirthDate());
        author.setEducation(dto.getEducation());
        author.setTitle(dto.getTitle());
        author.setSummary(dto.getSummary());
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setDeceasedDate(dto.getDeceasedDate());
        authorRepository.save(author);
    }

    @Override
    public UpsertAuthorRequestDto getAuthor(BigInteger id) {
        var author = authorRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Author Not found"));
        System.out.println(author);
        return UpsertAuthorRequestDto.builder()
                .id(author.getId())
                .birthDate(author.getBirthDate())
                .title(author.getTitle())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .deceasedDate(author.getDeceasedDate())
                .education(author.getEducation())
                .summary(author.getSummary())
                .fullName(author.getFullName())
                .build();
    }

    @Override
    public Boolean delete(BigInteger id) {
        var jumlahBuku = authorRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Tidak Ditemukan")).getBooks().size();
        System.out.println(jumlahBuku);
        if (jumlahBuku==0){
            authorRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<AuthorItemDto> getAutorsList() {
        var authorsPage = authorRepository.findAll();

        List<AuthorItemDto> authorItemList = new ArrayList<>();
        authorsPage.forEach(author -> {
            AuthorItemDto authorItemDto = AuthorItemDto.builder()
                    .id(author.getId())
                    .education(author.getEducation())
                    .fullName(author.getFullName())
                    .isLife(author.getDeceasedDate() == null ? "Alive" : "Deceased")
                    .age(author.getAge())
                    .build();
            authorItemList.add(authorItemDto);
        });
        return authorItemList;
    }

    @Override
    public List<BookItemDto> getBooks(BigInteger id) {
        var bookRepositorySearch = authorRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Tidak Ditemukan")).getBooks();

        List<BookItemDto> bookItemDtos = new ArrayList<>();
        bookRepositorySearch.forEach(book -> {
            var boookItemDto = BookItemDto.builder()
                    .title(book.getTitle())
                    .code(book.getCode())
                    .isBorrowed(book.getIsBorrowed())
                    .releaseDate(book.getReleaseDate())
                    .authorName(book.getAuthor().getFullName())
                    .totalPage(book.getTotalPage())
                    .summary(book.getSummary())
                    .categoryName(book.getCategory().getName())
                    .build();
            bookItemDtos.add(boookItemDto);
        });
        return bookItemDtos;
    }
}
