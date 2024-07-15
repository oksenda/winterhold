package com.oksenda.winterhold.controllers.api;

import com.oksenda.winterhold.dtos.AuthorPageRequestDto;
import com.oksenda.winterhold.dtos.UpsertAuthorRequestDto;
import com.oksenda.winterhold.services.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorControllerRest {
    private final AuthorService authorService;

    public AuthorControllerRest( AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAuthor(AuthorPageRequestDto dto){
        return ResponseEntity.ok(authorService.getAuthor(dto));
    }

    @GetMapping("books/{id}")
    public ResponseEntity<?> pageBooks(@PathVariable("id") BigInteger id){
        return ResponseEntity.ok(authorService.getBooks(id));
    }

    @PostMapping("")
    public ResponseEntity<?> insert(@Valid @RequestBody UpsertAuthorRequestDto dto){
        authorService.insert(dto);
        return ResponseEntity.ok("Behasil Menambahkan Auhtor");
    }
    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UpsertAuthorRequestDto dto,@PathVariable("id") BigInteger id){
        dto.setId(id);
        authorService.update(dto);
        return ResponseEntity.ok("Behasil Mengedit Auhtor");
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") BigInteger id, RedirectAttributes redirectAttributes){
        if (authorService.delete(id)){
            return ResponseEntity.ok("Behasil Menghapus Auhtor");
        }else {
            return ResponseEntity.ok("Behasil Gagal Menghapus Author karna masih ada buku yang terdaftar");
        }
    }
}
