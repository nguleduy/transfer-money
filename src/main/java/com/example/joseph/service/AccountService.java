package com.example.joseph.service;

import com.example.joseph.dto.account.AccountRequest;
import com.example.joseph.dto.account.AccountResponse;
import com.example.joseph.entity.Account;
import com.example.joseph.exception.AccountIsExistException;
import com.example.joseph.exception.AccountNotFoundException;
import com.example.joseph.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountService {

  private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

  private final AccountRepository accountRepository;

  protected void save(Account account) {
    accountRepository.save(account);
  }

  protected Account retrieve(String name) {
    Optional<Account> account = accountRepository.findByName(name);
    return account.orElseThrow(() -> new AccountNotFoundException(name));
  }

  public AccountResponse retrieveAccount(String name) {
    Account account = retrieve(name);
    return new AccountResponse(account.getId(), account.getName(), account.getBalance());
  }

  public AccountResponse createAccount(AccountRequest accountRequest) {
    Account existingAccount = retrieve(accountRequest.getName());
    if (existingAccount != null) {
      logger.info("Account Is Exist With Name: {}", existingAccount.getName());
      throw new AccountIsExistException(existingAccount.getName());
    }
    Account account = new Account(accountRequest.getName());
    save(account);
    logger.info("Account Created With Name: {}", account.getName());
    return new AccountResponse(account.getId(), account.getName(), account.getBalance());
  }

}
