package com.oksenda.winterhold.controllers.api;

import com.oksenda.winterhold.dtos.CategoryPageRequestDto;
import com.oksenda.winterhold.dtos.InsertCategoryRequesrDto;
import com.oksenda.winterhold.dtos.UpdateCategoriRequestDto;
import com.oksenda.winterhold.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryControllerRest {
    private final CategoryService categoryService;

    public CategoryControllerRest(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ResponseEntity<?> index( CategoryPageRequestDto dto){
        return ResponseEntity.ok(categoryService.getCatrgories(dto));
    }

    @PostMapping("")
    public ResponseEntity<?> insert(@Valid @RequestBody InsertCategoryRequesrDto dto){

        categoryService.insert(dto);
        return ResponseEntity.ok("Berhasil Menyimpan Category");
    }

    @PutMapping("{name}")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateCategoriRequestDto dto,@PathVariable("name")  String name){
        dto.setName(name);
        categoryService.update(dto);
        return ResponseEntity.ok("Berhasil Update Category");
    }
    @DeleteMapping("{name}")
    public ResponseEntity<?> delete(@PathVariable("name")  String name){
        if (categoryService.delete(name)){
            return ResponseEntity.ok("Berhasil Menghapus category");
        }else {
            return ResponseEntity.ok("Gagal Menghapus category, Categpry terdaftar di data buku");
        }
    }
}
