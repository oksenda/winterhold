package com.oksenda.winterhold.repositories;

import com.oksenda.winterhold.models.Book;
import com.oksenda.winterhold.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {
    @Query(value = """
            Select cus From Customer cus
            where (:membershipNumber IS NULL OR cus.membershipNumber LIKE %:membershipNumber%) and
            ((:name IS NULL OR cus.firstName LIKE %:name%) or
            (:name IS NULL OR cus.lastName LIKE %:name%))
            """)
    Page<Customer> findSearch(Pageable pageable,
                          @Param("name") String name,
                          @Param("membershipNumber") String membershipNumber);
    @Query(value = """
        SELECT cus FROM Customer cus
        WHERE cus.membershipExpireDate >= :today
        """)
    List<Customer> findAvailable(@Param("today") LocalDate today);

}
