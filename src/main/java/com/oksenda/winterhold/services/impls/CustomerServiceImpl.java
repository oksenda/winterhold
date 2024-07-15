package com.oksenda.winterhold.services.impls;

import com.oksenda.winterhold.dtos.*;
import com.oksenda.winterhold.models.Category;
import com.oksenda.winterhold.models.Customer;
import com.oksenda.winterhold.repositories.CustomerRepository;
import com.oksenda.winterhold.services.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Page<CustomerItemDto> getCustomers(CustomerPageRequestDto dto) {
        Pageable pageable = PageRequest.of(dto.getPageNumber() != null ? dto.getPageNumber() - 1 : 0, 10);
        var customersPage = customerRepository.findSearch(pageable, dto.getName(), dto.getMembershipNumber());

        List<CustomerItemDto> customerItemDtoList = new ArrayList<>();
       customersPage.forEach(customer -> {
            var customerItemDto = CustomerItemDto.builder()
                    .membershipNumber(customer.getMembershipNumber())
                    .membershipExpireDate(customer.getMembershipExpireDate())
                    .fullName(customer.getFullName())
                    .build();
            customerItemDtoList.add(customerItemDto);
        });

        return new PageImpl<>(customerItemDtoList, customersPage.getPageable(), customersPage.getTotalElements());
    }

    @Override
    public Boolean isExist(String membershipNumber) {
        return customerRepository.existsById(membershipNumber);
    }

    @Override
    public CustomerFieldDto getCustomer(String membershipNumber) {
        var customer = customerRepository.findById(membershipNumber).orElseThrow(()->new IllegalArgumentException("Customer not found"));
        return CustomerFieldDto.builder()
                .membershipNumber(customer.getMembershipNumber())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .membershipExpireDate(customer.getMembershipExpireDate())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .fullName(customer.getFullName())
                .gender(customer.getGender())
                .birthDate(customer.getBirthDate())
                .build();
    }

    @Override
    public void insert(CustomerRequestInsertDto dto) {
        if(customerRepository.existsById(dto.getMembershipNumber())){
            throw new IllegalArgumentException("Customer sudah terdaftar");
        }
        var customer = Customer.builder()
                .membershipNumber(dto.getMembershipNumber())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .membershipExpireDate(LocalDate.now().plusYears(2))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .gender(dto.getGender())
                .birthDate(dto.getBirthDate())
                .build();
        customerRepository.save(customer);

    }

    @Override
    public void update(CustomerRequestUpdateDto dto) {
        var customer = customerRepository.findById(dto.getMembershipNumber()).orElseThrow(()->new IllegalArgumentException("Customer not found"));
                customer.setAddress(dto.getAddress());
                customer.setPhone(dto.getPhone());
                customer.setFirstName(dto.getFirstName());
                customer.setLastName(dto.getLastName());
                customer.setGender(dto.getGender());
                customer.setBirthDate(dto.getBirthDate());
                customerRepository.save(customer);
    }

    @Override
    public Boolean isDelete(String membershipNumber) {
        var jumlahLoan = customerRepository.findById(membershipNumber).orElseThrow(()->new IllegalArgumentException("Customer not found")).getLoans().size();
        if (jumlahLoan==0){
            customerRepository.deleteById(membershipNumber);
            return true;
        }
        return false;
    }

    @Override
    public void updateExpireDate(String membershipNumber) {
        var customer = customerRepository.findById(membershipNumber).orElseThrow(()->new IllegalArgumentException("Customer not found"));
        customer.setMembershipExpireDate(LocalDate.now().plusYears(2));
        customerRepository.save(customer);
    }
}
