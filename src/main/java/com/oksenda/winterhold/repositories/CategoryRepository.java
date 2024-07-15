package com.oksenda.winterhold.repositories;

import com.oksenda.winterhold.models.Author;
import com.oksenda.winterhold.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,String> {
    @Query(value = """
            Select cat From Category cat
            where (:name IS NULL OR cat.name LIKE %:name%)
            """)
    Page<Category> findSearch(Pageable pageable,
                            @Param("name") String name
    );
}
