package com.example.joseph.repository;

import com.example.joseph.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

  Optional<Account> findByName(String name);

}
