package com.oksenda.winterhold.controllers.mvc;

import com.oksenda.winterhold.dtos.CategoryPageRequestDto;
import com.oksenda.winterhold.dtos.InsertCategoryRequesrDto;
import com.oksenda.winterhold.dtos.UpdateCategoriRequestDto;
import com.oksenda.winterhold.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("")
    public ModelAndView index(CategoryPageRequestDto dto){
        ModelAndView modelAndView = new ModelAndView("layout");
        modelAndView.addObject("contentFragment", "category/index :: content");
        modelAndView.addObject("dto",dto);
        modelAndView.addObject("categories",categoryService.getCatrgories(dto));
        modelAndView.addObject("title","Book Categories");
        return modelAndView;
    }
    @GetMapping("upsert")
    public ModelAndView pageUpsert(InsertCategoryRequesrDto dto){
        ModelAndView modelAndView = new ModelAndView("category/upsert-form");
        if (dto.getName()!=null && !dto.getName().isEmpty()){
            modelAndView.addObject("update",true);
            modelAndView.addObject("dto",categoryService.getCategory(dto.getName()));
        }else {
            modelAndView.addObject("dto", InsertCategoryRequesrDto.builder().build());
        }

        return modelAndView;
    }

    @PostMapping("insert")
    public ModelAndView insert(@Valid @ModelAttribute("dto") InsertCategoryRequesrDto dto, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("redirect:/categories");
        if (bindingResult.hasErrors()){
            modelAndView = new ModelAndView("category/upsert-form");
            modelAndView.addObject("dto",dto);
            return modelAndView;
        }
            categoryService.insert(dto);
        return modelAndView;
    }

    @PostMapping("update")
    public ModelAndView update(@Valid @ModelAttribute("dto") UpdateCategoriRequestDto dto, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("redirect:/categories");
        if (bindingResult.hasErrors()){
            modelAndView.addObject("update",true);
            modelAndView = new ModelAndView("category/upsert-form");
            modelAndView.addObject("dto",dto);
            return modelAndView;
        }
            categoryService.update(dto);
        return modelAndView;
    }
    @PostMapping("delete")
    public ModelAndView delete(@RequestParam String name, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("redirect:/categories");
        if (categoryService.delete(name)){
            redirectAttributes.addFlashAttribute("status",true);
            redirectAttributes.addFlashAttribute("message","Berhasil Menghapus Data Category");
        }else {
            redirectAttributes.addFlashAttribute("status",false);
            redirectAttributes.addFlashAttribute("message","Gagal Menghapus Data Category, Category memiliki buku yang terdaftar");
        }
        return modelAndView;
    }
}
