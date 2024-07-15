package com.oksenda.winterhold.services;

import com.oksenda.winterhold.dtos.CategoryPageRequestDto;
import com.oksenda.winterhold.dtos.CategoryItemDto;
import com.oksenda.winterhold.dtos.InsertCategoryRequesrDto;
import com.oksenda.winterhold.dtos.UpdateCategoriRequestDto;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<CategoryItemDto> getCatrgories(CategoryPageRequestDto dto);
    InsertCategoryRequesrDto getCategory(String name);

    void insert(InsertCategoryRequesrDto dto);
    void update(UpdateCategoriRequestDto dto);
    Boolean delete(String name);

    boolean isCategoryExist(String username);
}
