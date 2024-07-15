package com.oksenda.winterhold.controllers.mvc;

import com.oksenda.winterhold.dtos.AuthorPageRequestDto;
import com.oksenda.winterhold.dtos.UpsertAuthorRequestDto;
import com.oksenda.winterhold.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("")
    public ModelAndView index(AuthorPageRequestDto dto){
        ModelAndView modelAndView = new ModelAndView("layout");
        modelAndView.addObject("contentFragment", "author/index :: content");
        modelAndView.addObject("dto",dto);
        modelAndView.addObject("authors",authorService.getAuthor(dto));
        modelAndView.addObject("title","Authors");
        return modelAndView;
    }
    @GetMapping("upsert")
    public ModelAndView pageUpsert(UpsertAuthorRequestDto dto){
        ModelAndView modelAndView = new ModelAndView("author/upsert-form");
        if (dto.getId()!=null){
            modelAndView.addObject("dto",authorService.getAuthor(dto.getId()));
            System.out.println(authorService.getAuthor(dto.getId()));
        }else {
            modelAndView.addObject("dto", UpsertAuthorRequestDto.builder().build());
        }

        return modelAndView;
    }

    @GetMapping("books")
    public ModelAndView pageBooks(@RequestParam BigInteger id){
        ModelAndView modelAndView = new ModelAndView("author/books");
        modelAndView.addObject("books",authorService.getBooks(id));
        modelAndView.addObject("author",authorService.getAuthor(id));
        return modelAndView;
    }

    @PostMapping("upsert")
    public ModelAndView upsert(@Valid @ModelAttribute("dto") UpsertAuthorRequestDto dto, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("redirect:/authors");
        if (bindingResult.hasErrors()){
            modelAndView = new ModelAndView("author/upsert-form");
            modelAndView.addObject("dto",dto);
            return modelAndView;
        }

        if (dto.getId()!=null){
            authorService.update(dto);
        }else {
            authorService.insert(dto);
        }
        return modelAndView;
    }
    @PostMapping("delete")
    public ModelAndView delete(@RequestParam BigInteger id, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("redirect:/authors");
        if (authorService.delete(id)){
            redirectAttributes.addFlashAttribute("status",true);
            redirectAttributes.addFlashAttribute("message","Berhasil Menghapus Data Author");
        }else {
            redirectAttributes.addFlashAttribute("status",false);
            redirectAttributes.addFlashAttribute("message","Gagal Menghapus Data Author, Author memiliki buku yang terdaftar");
        }
        return modelAndView;
    }
}
