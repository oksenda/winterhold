package com.oksenda.winterhold.services.impls;

import com.oksenda.winterhold.dtos.*;
import com.oksenda.winterhold.models.Author;
import com.oksenda.winterhold.models.Book;
import com.oksenda.winterhold.models.Category;
import com.oksenda.winterhold.repositories.AuthorRepository;
import com.oksenda.winterhold.repositories.BookRepository;
import com.oksenda.winterhold.repositories.CategoryRepository;
import com.oksenda.winterhold.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Page<BookItemDto> getBooks(BookPageRequestDto dto) {
        Pageable pageable = PageRequest.of(dto.getPageNumber() != null ? dto.getPageNumber() - 1 : 0, 10);
        var bookRepositorySearch = bookRepository.findSearch(pageable, dto.getName(), dto.getTitle(),dto.getCategoryName(),dto.getIsBorrowed());

        List<BookItemDto> bookItemDtos = new ArrayList<>();
        bookRepositorySearch.forEach(book -> {
            var boookItemDto = BookItemDto.builder()
                    .title(book.getTitle())
                    .code(book.getCode())
                    .isBorrowed(book.getIsBorrowed())
                    .releaseDate(book.getReleaseDate())
                    .authorName(book.getAuthors().stream().map(
                            author -> author.getFullName()
                    ).toList())
                    .totalPage(book.getTotalPage())
                    .summary(book.getSummary())
                    .build();
            bookItemDtos.add(boookItemDto);
        });

        return new PageImpl<>(bookItemDtos, bookRepositorySearch.getPageable(), bookRepositorySearch.getTotalElements());
    }

    @Override
    public void insert(InsertRequestBookDto dto) {
        if(bookRepository.existsById(dto.getCode())){
            throw new IllegalArgumentException("categori sudah terdaftar");
        }
        List<Author> authors = dto.getAuthorId().stream()
                .map(authorId -> authorRepository.findById(authorId)
                        .orElseThrow(() -> new RuntimeException("Author not found with id " + authorId))).toList();
        var book = Book.builder()
                .title(dto.getTitle())
                .category(categoryRepository.findById(dto.getCategoryName()).orElseThrow(()->new IllegalArgumentException("Category Not found")))
                .authors(authors)
                .code(dto.getCode())
                .isBorrowed(false)
                .releaseDate(dto.getReleaseDate())
                .summary(dto.getSummary())
                .totalPage(dto.getTotalPage())
                .build();
        bookRepository.save(book);

    }

    @Override
    public void update(UpdateBookRequestDto dto) {
        var book = bookRepository.findById(dto.getCode()).orElseThrow(()->new IllegalArgumentException("Book Not found"));
        book.setAuthor(authorRepository.findById(dto.getAuthorId()).orElseThrow(()->new IllegalArgumentException("Author Not found")));
        book.setTitle(dto.getTitle());
        book.setReleaseDate(dto.getReleaseDate());
        book.setTotalPage(dto.getTotalPage());
        book.setSummary(dto.getSummary());
        bookRepository.save(book);

    }

    @Override
    public UpdateBookRequestDto getBook(String code) {
        var book = bookRepository.findById(code).orElseThrow(()->new IllegalArgumentException("Book Not found"));
        return UpdateBookRequestDto.builder()
                .title(book.getTitle())
                .authorId(book.getAuthor().getId())
                .categoryName(book.getCategory().getName())
                .totalPage(book.getTotalPage())
                .code(book.getCode())
                .releaseDate(book.getReleaseDate())
                .summary(book.getSummary())
                .build();
    }

    @Override
    public Boolean delete(String code) {
        var totalLoan = bookRepository.findById(code).orElseThrow(()->new IllegalArgumentException("Book Not found")).getLoans().size();
        if (totalLoan==0){
            bookRepository.deleteById(code);
            return true;
        }
        return false;
    }

    @Override
    public boolean isExist(String code) {
        return bookRepository.existsById(code);
    }
}
