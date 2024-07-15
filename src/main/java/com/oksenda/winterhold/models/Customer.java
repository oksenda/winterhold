package com.oksenda.winterhold.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @Column(name = "MembershipNumber")
    private String membershipNumber;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "BirthDate")
    private LocalDate birthDate;
    @Column(name = "Gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Address")
    private String address;
    @Column(name = "MembershipExpireDate")
    private LocalDate membershipExpireDate;
    @OneToMany(mappedBy = "customer")
    private List<Loan> loans;

    public String getFullName(){
        return String.format("%s %s",firstName,lastName);
    }
}
