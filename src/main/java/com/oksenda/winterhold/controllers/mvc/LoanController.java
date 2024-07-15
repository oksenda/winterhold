package com.oksenda.winterhold.controllers.mvc;

import com.oksenda.winterhold.dtos.*;
import com.oksenda.winterhold.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;

@Controller
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("")
    public ModelAndView index(LoanPageRequeatDto dto){
        ModelAndView modelAndView = new ModelAndView("layout");
        modelAndView.addObject("contentFragment", "loan/index :: content");
        modelAndView.addObject("dto",dto);
        modelAndView.addObject("loans",loanService.getLoans(dto));
        modelAndView.addObject("title","Loan Page");
        return modelAndView;
    }
    @GetMapping("upsert")
    public ModelAndView pageUpsert(LoanFieldDto dto){
        ModelAndView modelAndView = new ModelAndView("loan/upsert-form");
        modelAndView.addObject("books",loanService.getBookList());
        modelAndView.addObject("customers",loanService.getCustomerList());
        if (dto.getId()!=null){
            modelAndView.addObject("dto",loanService.getLoan(dto.getId()));
        }else {
            modelAndView.addObject("dto", LoanFieldDto.builder().build());
        }

        return modelAndView;
    }

    @GetMapping("detail")
    public ModelAndView pageDetail(LoanFieldDto dto){
        ModelAndView modelAndView = new ModelAndView("loan/detail");
        modelAndView.addObject("book",loanService.getBookDetail(dto.getId()));
        modelAndView.addObject("customer",loanService.getCustomerDetail(dto.getId()));
        modelAndView.addObject("loan",loanService.getLoan(dto.getId()));
        return modelAndView;
    }


    @PostMapping("upsert")
    public ModelAndView upsert(@Valid @ModelAttribute("dto") LoanUpsertRequestDto dto, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("redirect:/loans");
        if (bindingResult.hasErrors()){
            modelAndView = new ModelAndView("loan/upsert-form");
            modelAndView.addObject("books",loanService.getBookList());
            modelAndView.addObject("customers",loanService.getCustomerList());
            modelAndView.addObject("dto",dto);
            return modelAndView;
        }

        if (dto.getId()!=null){
            loanService.update(dto);
        }else {
            loanService.insert(dto);
        }
        return modelAndView;
    }

    @PostMapping("return")
    public ModelAndView delete(@RequestParam BigInteger id){
        ModelAndView modelAndView = new ModelAndView("redirect:/loans");
        loanService.returnLoan(id);
        return modelAndView;
    }
}
