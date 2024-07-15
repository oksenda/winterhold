package com.oksenda.winterhold.repositories;

import com.oksenda.winterhold.models.Author;
import com.oksenda.winterhold.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    @Query(value = """
            Select b From Book b
            where (:title IS NULL OR b.title LIKE %:title%) and
            ((:name IS NULL OR b.author.firstName LIKE %:name%) or
            (:name IS NULL OR b.author.lastName LIKE %:name%)) and
            (:categoryName IS NULL OR b.category.name LIKE %:categoryName%)and
            (:isBorrowed IS NULL OR b.isBorrowed = :isBorrowed)
            """)
    Page<Book> findSearch(Pageable pageable,
                            @Param("name") String name,
                            @Param("title") String title,
                          @Param("categoryName") String categoryName,
                          @Param("isBorrowed") Boolean isBorrowed
    );
    @Query(value = """
            Select b From Book b
            where isBorrowed = false
            """)
    List<Book> findListAvailable();
}
