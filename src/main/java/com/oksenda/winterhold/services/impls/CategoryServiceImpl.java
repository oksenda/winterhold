package com.oksenda.winterhold.services.impls;

import com.oksenda.winterhold.dtos.CategoryPageRequestDto;
import com.oksenda.winterhold.dtos.CategoryItemDto;
import com.oksenda.winterhold.dtos.InsertCategoryRequesrDto;
import com.oksenda.winterhold.dtos.UpdateCategoriRequestDto;
import com.oksenda.winterhold.models.Category;
import com.oksenda.winterhold.repositories.CategoryRepository;
import com.oksenda.winterhold.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<CategoryItemDto> getCatrgories(CategoryPageRequestDto dto) {
        Pageable pageable = PageRequest.of(dto.getPageNumber() != null ? dto.getPageNumber() - 1 : 0, 10);
        var categoryPage = categoryRepository.findSearch(pageable, dto.getName());

        List<CategoryItemDto> categoryItemDtos = new ArrayList<>();
        categoryPage.forEach(category -> {
            var categoryItemDto = CategoryItemDto.builder()
                    .name(category.getName())
                    .bay(category.getBay())
                    .floor(category.getFloor())
                    .isle(category.getIsle())
                    .totalBooks(category.getTotalBook())
                    .build();
            categoryItemDtos.add(categoryItemDto);
        });

        return new PageImpl<>(categoryItemDtos, categoryPage.getPageable(), categoryPage.getTotalElements());
    }

    @Override
    public InsertCategoryRequesrDto getCategory(String name) {
        var category = categoryRepository.findById(name).orElseThrow(()->new IllegalArgumentException("Category Not found"));
        return InsertCategoryRequesrDto.builder()
                .name(category.getName())
                .bay(category.getBay())
                .floor(category.getFloor())
                .isle(category.getIsle())
                .build();
    }
    @Override
    public void insert(InsertCategoryRequesrDto dto) {
      if(categoryRepository.existsById(dto.getName())){
            throw new IllegalArgumentException("categori sudah terdaftar");
        }
        var category = Category.builder()
                .name(dto.getName())
                .bay(dto.getBay())
                .floor(dto.getFloor())
                .isle(dto.getIsle())
                .build();
        categoryRepository.save(category);
    }

    @Override
    public void update(UpdateCategoriRequestDto dto) {
        var category = categoryRepository.findById(dto.getName()).orElseThrow(()->new IllegalArgumentException("Category Not found"));
        category.setBay(dto.getBay());
        category.setIsle(dto.getIsle());
        category.setFloor(dto.getFloor());
        categoryRepository.save(category);
    }

    @Override
    public Boolean delete(String name) {
        var jumlahBuku = categoryRepository.findById(name).orElseThrow(()->new IllegalArgumentException("Category Not found")).getTotalBook();
        if (jumlahBuku==0){
            categoryRepository.deleteById(name);
            return true;
        }

        return false;
    }

    @Override
    public boolean isCategoryExist(String name) {
        return categoryRepository.existsById(name);
    }
}
