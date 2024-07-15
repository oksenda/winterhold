package com.oksenda.winterhold.controllers.api;

import com.oksenda.winterhold.dtos.LoanFieldDto;
import com.oksenda.winterhold.dtos.LoanPageRequeatDto;
import com.oksenda.winterhold.dtos.LoanUpsertRequestDto;
import com.oksenda.winterhold.services.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;

@RestController
@RequestMapping("api/v1/loans")
public class LoanControllerRest {
    private final LoanService loanService;

    public LoanControllerRest(LoanService loanService) {
        this.loanService = loanService;
    }


    @GetMapping("")
    public ResponseEntity<?> gatLoans(LoanPageRequeatDto dto){
        return ResponseEntity.ok(loanService.getLoans(dto));
    }
    @GetMapping("book/{id}")
    public ResponseEntity<?> getBook(@PathVariable("id") BigInteger id){
        return ResponseEntity.ok(loanService.getBookDetail(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> gatLoans(@PathVariable("id") BigInteger id){
        return ResponseEntity.ok(loanService.getLoan(id));
    }

    @GetMapping("customer/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") BigInteger id){
        return ResponseEntity.ok(loanService.getCustomerDetail(id));
    }



    @PostMapping()
    public ResponseEntity<?> insert(@Valid @RequestBody LoanUpsertRequestDto dto){
        loanService.insert(dto);
        return ResponseEntity.ok("Berhasil Insert Loan");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody LoanUpsertRequestDto dto,@PathVariable("id") BigInteger id){
        dto.setId(id);
        loanService.update(dto);
        return ResponseEntity.ok("Berhasil update Loan");
    }

    @PostMapping("return/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") BigInteger id){
        loanService.returnLoan(id);
        return ResponseEntity.ok("Berhasil return loan");
    }
}
