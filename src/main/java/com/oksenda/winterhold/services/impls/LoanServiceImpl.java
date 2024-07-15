package com.oksenda.winterhold.services.impls;

import com.oksenda.winterhold.dtos.*;
import com.oksenda.winterhold.models.Customer;
import com.oksenda.winterhold.models.Loan;
import com.oksenda.winterhold.repositories.BookRepository;
import com.oksenda.winterhold.repositories.CustomerRepository;
import com.oksenda.winterhold.repositories.LoanRepository;
import com.oksenda.winterhold.services.CustomerService;
import com.oksenda.winterhold.services.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository, CustomerRepository customerRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Page<LoanItemDto> getLoans(LoanPageRequeatDto dto) {
        Pageable pageable = PageRequest.of(dto.getPageNumber() != null ? dto.getPageNumber() - 1 : 0, 10);
        var loansPage = loanRepository.findSearch(pageable, dto.getCustomerName(), dto.getBookTitle());

        List<LoanItemDto> loanItemDtoList = new ArrayList<>();
        loansPage.forEach(loan -> {
            var loanItemDto = LoanItemDto.builder()
                    .id(loan.getId())
                    .bookTitle(loan.getBook().getTitle())
                    .loanDate(loan.getLoanDate())
                    .customerName(loan.getCustomer().getFullName())
                    .dueDate(loan.getDueDate())
                    .returnDate(loan.getReturnDate())
                    .build();
            loanItemDtoList.add(loanItemDto);
        });

        return new PageImpl<>(loanItemDtoList, loansPage.getPageable(), loansPage.getTotalElements());
    }

    @Override
    public LoanFieldDto getLoan(BigInteger id) {
        var loan = loanRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Loan not found"));
        return LoanFieldDto.builder()
                .id(loan.getId())
                .loanDate(loan.getLoanDate())
                .dueDate(loan.getDueDate())
                .bookCode(loan.getBook().getCode())
                .customerNumber(loan.getCustomer().getMembershipNumber())
                .returnDate(loan.getReturnDate())
                .note(loan.getNote())
                .build();
    }

    @Override
    public void insert(LoanUpsertRequestDto dto) {
        var book =bookRepository.findById(dto.getBookCode()).orElseThrow(()->new IllegalArgumentException("Book Not Found"));
        book.setIsBorrowed(true);
        var loan = Loan.builder()
                .loanDate(dto.getLoanDate())
                .dueDate(dto.getLoanDate().plusDays(5))
                .book(bookRepository.findById(dto.getBookCode()).orElseThrow(()->new IllegalArgumentException("Book Not Found")))
                .customer(customerRepository.findById(dto.getCustomerNumber()).orElseThrow(()->new IllegalArgumentException("customer Not Found")))
                .note(dto.getNote())
                .build();
        loanRepository.save(loan);
        bookRepository.save(book);

    }

    @Override
    public void update(LoanUpsertRequestDto dto) {
        var loan = loanRepository.findById(dto.getId()).orElseThrow(()->new IllegalArgumentException("Loan not found"));
        loan.setLoanDate(dto.getLoanDate());
        loan.setBook(bookRepository.findById(dto.getBookCode()).orElseThrow(()->new IllegalArgumentException("Book Not Found")));
        loan.setCustomer(customerRepository.findById(dto.getCustomerNumber()).orElseThrow(()->new IllegalArgumentException("customer Not Found")));
        loan.setNote(dto.getNote());
        loanRepository.save(loan);
    }


    @Override
    public void returnLoan(BigInteger id) {
        var loan = loanRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Loan not found"));
        var book =loan.getBook();
        book.setIsBorrowed(false);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
        bookRepository.save(book);
    }

    @Override
    public List<BookItemDto> getBookList() {
        var books = bookRepository.findListAvailable();
        List<BookItemDto> bookItemDtos = new ArrayList<>();
        books.forEach(book -> {
            var boookItemDto = BookItemDto.builder()
                    .title(book.getTitle())
                    .code(book.getCode())
                    .build();
            bookItemDtos.add(boookItemDto);
        });
        return bookItemDtos;
    }

    @Override
    public List<CustomerItemDto> getCustomerList() {
        var customers = customerRepository.findAvailable(LocalDate.now());
        List<CustomerItemDto> customerItemDtoList = new ArrayList<>();
        customers.forEach(customer -> {
            var customerItemDto = CustomerItemDto.builder()
                    .membershipNumber(customer.getMembershipNumber())
                    .membershipExpireDate(customer.getMembershipExpireDate())
                    .fullName(customer.getFullName())
                    .build();
            customerItemDtoList.add(customerItemDto);
        });
        return customerItemDtoList;
    }

    @Override
    public LoanBookDetailDto getBookDetail(BigInteger id) {
        var loan = loanRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Loan not found"));
        var book = loan.getBook();
        return LoanBookDetailDto.builder()
                .title(book.getTitle())
                .totalPage(book.getTotalPage())
                .authorName(book.getAuthor().getFullName())
                .categoryName(book.getCategory().getName())
                .bay(book.getCategory().getBay())
                .floor(book.getCategory().getFloor())
                .isle(book.getCategory().getIsle())
                .build();
    }

    @Override
    public LoanCustomerDetailDto getCustomerDetail(BigInteger id) {
        var loan = loanRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Loan not found"));
        var customer = loan.getCustomer();
        return LoanCustomerDetailDto.builder()
                .fullName(customer.getFullName())
                .phone(customer.getPhone())
                .membershipNumber(customer.getMembershipNumber())
                .membershipExpireDate(customer.getMembershipExpireDate())
                .build();
    }
}
