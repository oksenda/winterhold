package com.oksenda.winterhold.repositories;

import com.oksenda.winterhold.models.Customer;
import com.oksenda.winterhold.models.Loan;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface LoanRepository extends JpaRepository<Loan, BigInteger> {
    @Query(value = """
            Select l From Loan l
            where (:bookTitle IS NULL OR l.book.title LIKE %:bookTitle%) and
            ((:customerName IS NULL OR l.customer.firstName LIKE %:customerName%) or
            (:customerName IS NULL OR l.customer.lastName LIKE %:customerName%))
            """)
    Page<Loan> findSearch(Pageable pageable,
                              @Param("customerName") String customerName,
                              @Param("bookTitle") String bookTitle);
}
