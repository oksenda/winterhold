package com.oksenda.winterhold.controllers.api;

import com.oksenda.winterhold.dtos.BookPageRequestDto;
import com.oksenda.winterhold.dtos.InsertRequestBookDto;
import com.oksenda.winterhold.dtos.UpdateBookRequestDto;
import com.oksenda.winterhold.services.AuthorService;
import com.oksenda.winterhold.services.BookService;
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
@RequestMapping("api/v1/books")
public class BookControllerRest {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookControllerRest(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }


    @GetMapping("")
    public ResponseEntity<?> getBooks( BookPageRequestDto dto){
        return ResponseEntity.ok(bookService.getBooks(dto));
    }

    @PostMapping("")
    public ResponseEntity<?> insert(@Valid @RequestBody InsertRequestBookDto dto){
        bookService.insert(dto);
        return ResponseEntity.ok("Berhasil Menginput buku");
    }

    @PutMapping("{code}")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateBookRequestDto dto,@PathVariable("code") String code){
        dto.setCode(code);
        bookService.update(dto);
        return ResponseEntity.ok("Berhasil Mengedit Data buku");
    }
    @DeleteMapping("{code}")
    public ResponseEntity<?> delete(@PathVariable("code") String code){
        if (bookService.delete(code)){
            return ResponseEntity.ok("Berhasil Menghapus buku");
        }else {
            return ResponseEntity.ok("Gagal menghapus buku, buku terdaftar di loan");
        }
    }
}
