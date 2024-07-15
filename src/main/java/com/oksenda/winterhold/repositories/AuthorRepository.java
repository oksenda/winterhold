package com.oksenda.winterhold.repositories;

import com.oksenda.winterhold.models.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface AuthorRepository extends JpaRepository<Author, BigInteger> {
    @Query(value = """
            Select ath From Author ath
            where (:name IS NULL OR ath.firstName LIKE %:name%) or
            (:name IS NULL OR ath.lastName LIKE %:name%) or
            (:name IS NULL OR ath.title LIKE %:name%)
            """)
    Page<Author> findSearch(Pageable pageable,
                              @Param("name") String name
    );
}
