package com.oksenda.winterhold.controllers.api;

import com.oksenda.winterhold.dtos.CustomerFieldDto;
import com.oksenda.winterhold.dtos.CustomerPageRequestDto;
import com.oksenda.winterhold.dtos.CustomerRequestInsertDto;
import com.oksenda.winterhold.dtos.CustomerRequestUpdateDto;
import com.oksenda.winterhold.services.CustomerService;
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
@RequestMapping("api/v1/customers")
public class CustomerControllerRest {
    private final CustomerService customerService;

    public CustomerControllerRest(CustomerService customerService) {
        this.customerService = customerService;
    }



    @GetMapping("")
    public ResponseEntity<?> getCustumers(CustomerPageRequestDto dto){
        return ResponseEntity.ok(customerService.getCustomers(dto));
    }

    @GetMapping("detail/{membershipNumber}")
    public ResponseEntity<?> getDetail(@PathVariable("membershipNumber")  String membershipNumber){
        return ResponseEntity.ok(customerService.getCustomer(membershipNumber));
    }

    @PostMapping("")
    public ResponseEntity<?> insert(@Valid @RequestBody CustomerRequestInsertDto dto){
        customerService.insert(dto);
        return ResponseEntity.ok("Berhasil insert data customer");
    }

    @PutMapping("{membershipNumber}")
    public ResponseEntity<?> update(@Valid @RequestBody CustomerRequestUpdateDto dto,@PathVariable("membershipNumber")  String membershipNumber){
        dto.setMembershipNumber(membershipNumber);
        customerService.update(dto);
        return ResponseEntity.ok("Berhasil update data customer");
    }
    @PutMapping("extend/{membershipNumber}")
    public ResponseEntity<?>  extend(@PathVariable("membershipNumber")  String membershipNumber){
        customerService.updateExpireDate(membershipNumber);
        return ResponseEntity.ok("Berhasil extend membership");
    }
    @PostMapping("{membershipNumber}")
    public ResponseEntity<?> delete(@PathVariable("membershipNumber") String membershipNumber){
        if (customerService.isDelete(membershipNumber)){
            return ResponseEntity.ok("Berhasil Menghapus Data Customer");
        }else {
            return ResponseEntity.ok("Gagal Menghapus Data customer, customer memiliki Loan yang terdaftar");
        }
    }
}
