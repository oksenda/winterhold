package com.oksenda.winterhold.controllers.mvc;

import com.oksenda.winterhold.dtos.*;
import com.oksenda.winterhold.services.CategoryService;
import com.oksenda.winterhold.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("")
    public ModelAndView index(CustomerPageRequestDto dto){
        ModelAndView modelAndView = new ModelAndView("layout");
        modelAndView.addObject("contentFragment", "customer/index :: content");
        modelAndView.addObject("dto",dto);
        modelAndView.addObject("customers",customerService.getCustomers(dto));
        modelAndView.addObject("title","Customer Page");
        return modelAndView;
    }
    @GetMapping("upsert")
    public ModelAndView pageUpsert(CustomerFieldDto dto){
        ModelAndView modelAndView = new ModelAndView("customer/upsert-form");
        if (dto.getMembershipNumber()!=null && !dto.getMembershipNumber().isEmpty()){
            modelAndView.addObject("update",true);
            modelAndView.addObject("dto",customerService.getCustomer(dto.getMembershipNumber()));
        }else {
            modelAndView.addObject("dto", CustomerFieldDto.builder().build());
        }

        return modelAndView;
    }

    @GetMapping("detail")
    public ModelAndView pageDetail(CustomerFieldDto dto){
        ModelAndView modelAndView = new ModelAndView("customer/detail");
        modelAndView.addObject("customer",customerService.getCustomer(dto.getMembershipNumber()));
        return modelAndView;
    }

    @PostMapping("insert")
    public ModelAndView insert(@Valid @ModelAttribute("dto") CustomerRequestInsertDto dto, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("redirect:/customers");
        if (bindingResult.hasErrors()){
            modelAndView = new ModelAndView("customer/upsert-form");
            modelAndView.addObject("dto",dto);
            return modelAndView;
        }
        customerService.insert(dto);
        return modelAndView;
    }

    @PostMapping("update")
    public ModelAndView update(@Valid @ModelAttribute("dto") CustomerRequestUpdateDto dto, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("redirect:/customers");
        if (bindingResult.hasErrors()){
            modelAndView = new ModelAndView("customer/upsert-form");
            modelAndView.addObject("update",true);
            modelAndView.addObject("dto",dto);
            return modelAndView;
        }
        customerService.update(dto);
        return modelAndView;
    }
    @PostMapping("extend")
    public ModelAndView extend(@RequestParam String membershipNumber){
        ModelAndView modelAndView = new ModelAndView("redirect:/customers");
        customerService.updateExpireDate(membershipNumber);
        return modelAndView;
    }
    @PostMapping("delete")
    public ModelAndView delete(@RequestParam String membershipNumber, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("redirect:/customers");
        if (customerService.isDelete(membershipNumber)){
            redirectAttributes.addFlashAttribute("status",true);
            redirectAttributes.addFlashAttribute("message","Berhasil Menghapus Data Customer");
        }else {
            redirectAttributes.addFlashAttribute("status",false);
            redirectAttributes.addFlashAttribute("message","Gagal Menghapus Data customer, customer memiliki Loan yang terdaftar");
        }
        return modelAndView;
    }
}
