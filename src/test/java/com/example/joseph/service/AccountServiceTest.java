package com.example.joseph.service;

import com.example.joseph.dto.account.AccountRequest;
import com.example.joseph.dto.account.AccountResponse;
import com.example.joseph.entity.Account;
import com.example.joseph.exception.AccountNotFoundException;
import com.example.joseph.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

  @InjectMocks
  private AccountService accountService;

  @Mock
  private AccountRepository accountRepository;

  @Test
  public void testCreateAccount() {
    AccountRequest accountRequest = new AccountRequest();
    accountRequest.setName("name");

    AccountResponse accountResponse = accountService.createAccount(accountRequest);

    Assert.assertNotNull(accountResponse);
    Assert.assertNotNull(accountResponse.getResult());
    Assert.assertEquals(0, accountResponse.getResult().getCode().intValue());

    Account account = new Account();
    account.setName(accountRequest.getName());
    account.setBalance(BigDecimal.TEN);
    Mockito.verify(accountRepository).save((Account) ArgumentMatchers.argThat(new ReflectionEquals(account, "id")));
  }

  @Test(expected = AccountNotFoundException.class)
  public void testAccountNotFound() {
    String fromAccountName = "name";

    Mockito.when(accountRepository.findByName(fromAccountName)).thenReturn(Optional.empty());

    accountService.retrieve(fromAccountName);
  }

}
