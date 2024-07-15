package com.oksenda.winterhold.controllers.mvc;

import com.oksenda.winterhold.dtos.*;
import com.oksenda.winterhold.services.AuthorService;
import com.oksenda.winterhold.services.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("")
    public ModelAndView index(BookPageRequestDto dto){
        ModelAndView modelAndView = new ModelAndView("layout");
        modelAndView.addObject("contentFragment", "book/index :: content");
        modelAndView.addObject("dto",dto);
        modelAndView.addObject("books",bookService.getBooks(dto));
        modelAndView.addObject("title","Books "+dto.getCategoryName());
        return modelAndView;
    }
    @GetMapping("upsert")
    public ModelAndView pageUpsert(UpdateBookRequestDto dto){
        ModelAndView modelAndView = new ModelAndView("book/upsert-form");
        modelAndView.addObject("authors",authorService.getAutorsList());
        if (dto.getCode()!=null && !dto.getCode().isEmpty()){
            modelAndView.addObject("update",true);
            modelAndView.addObject("dto",bookService.getBook(dto.getCode()));
        }else {
            modelAndView.addObject("dto", UpdateBookRequestDto.builder()
                            .categoryName(dto.getCategoryName()).build());
        }


        return modelAndView;
    }

    @GetMapping("summary")
    public ModelAndView pageSummaty(UpdateBookRequestDto dto){
        ModelAndView modelAndView = new ModelAndView("book/summary");
        modelAndView.addObject("authors",authorService.getAutorsList());
            modelAndView.addObject("book",bookService.getBook(dto.getCode()));
        return modelAndView;
    }

    @PostMapping("insert")
    public ModelAndView insert(@Valid @ModelAttribute("dto") InsertRequestBookDto dto, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("redirect:/books?categoryName="+dto.getCategoryName());
        if (bindingResult.hasErrors()){
            modelAndView = new ModelAndView("book/upsert-form");
            modelAndView.addObject("authors",authorService.getAutorsList());
            modelAndView.addObject("dto",dto);
            return modelAndView;
        }
        bookService.insert(dto);
        return modelAndView;
    }

    @PostMapping("update")
    public ModelAndView update(@Valid @ModelAttribute("dto") UpdateBookRequestDto dto, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("redirect:/books?categoryName="+dto.getCategoryName());
        if (bindingResult.hasErrors()){
            modelAndView = new ModelAndView("book/upsert-form");
            modelAndView.addObject("update",true);
            modelAndView.addObject("authors",authorService.getAutorsList());
            modelAndView.addObject("dto",dto);
            return modelAndView;
        }
        bookService.update(dto);
        return modelAndView;
    }
    @PostMapping("delete")
    public ModelAndView delete(@RequestParam String code, RedirectAttributes redirectAttributes){
        var categoryName = bookService.getBook(code).getCategoryName();
        ModelAndView modelAndView =new ModelAndView("redirect:/books?categoryName="+categoryName);
        if (bookService.delete(code)){
            redirectAttributes.addFlashAttribute("status",true);
            redirectAttributes.addFlashAttribute("message","Berhasil Menghapus Data Buku");
        }else {
            redirectAttributes.addFlashAttribute("status",false);
            redirectAttributes.addFlashAttribute("message","Gagal Menghapus Data Buku, Buku memiliki Loan yang terdaftar");
        }
        return modelAndView;
    }
}
