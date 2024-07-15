package com.oksenda.winterhold.models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Account")
public class Account {
    @Id
    @Column(name = "Username")
    private String username;
    @Column(name = "Password")
    private String password;

}