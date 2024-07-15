package com.oksenda.winterhold.repositories;

import com.oksenda.winterhold.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
