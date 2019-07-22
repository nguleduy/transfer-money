package com.example.joseph.integration;

import com.example.joseph.Application;
import com.example.joseph.dto.transfer.TransferRequest;
import com.example.joseph.dto.transfer.TransferResponse;
import com.example.joseph.entity.Account;
import com.example.joseph.entity.Transfer;
import com.example.joseph.exception.NegativeBalanceException;
import com.example.joseph.repository.AccountRepository;
import com.example.joseph.repository.TransferRepository;
import com.example.joseph.service.TransferService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TransferServiceTest {

  private static final String FROM_NAME = "fromName";
  private static final String TO_NAME = "toName";

  @Autowired
  private TransferService transferService;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransferRepository transferRepository;

  @Before
  public void setUp() {
    Account fromAccount = new Account(FROM_NAME);
    fromAccount.setBalance(BigDecimal.valueOf(50));
    accountRepository.save(fromAccount);

    Account toAccount = new Account(TO_NAME);
    accountRepository.save(toAccount);
  }

  private TransferRequest createTransferRequest(Account from, Account to, BigDecimal amount) {
    TransferRequest transferRequest = new TransferRequest();
    transferRequest.setAmount(amount);
    transferRequest.setFromName(from.getName());
    transferRequest.setToName(to.getName());
    return transferRequest;
  }

  @Test
  public void testTransferMoney() {
    Optional<Account> fromAccount = accountRepository.findByName(FROM_NAME);
    Optional<Account> toAccount = accountRepository.findByName(TO_NAME);

    TransferRequest transferRequest = createTransferRequest(fromAccount.get(),
            toAccount.get(),
            BigDecimal.TEN);
    TransferResponse transferResponse = transferService.transfer(transferRequest);

    Assert.assertNotNull(transferResponse);
    Assert.assertNotNull(transferResponse.getResult());
    Assert.assertEquals(0, transferResponse.getResult().getCode().intValue());

    Optional<Account> updatedFromAccount = accountRepository.findByName(FROM_NAME);
    Optional<Account> updatedToAccount = accountRepository.findByName(TO_NAME);
    Optional<Transfer> transfer = transferRepository.findById(transferResponse.getId());

    Assert.assertEquals(40, updatedFromAccount.get().getBalance().intValue());
    Assert.assertEquals(20, updatedToAccount.get().getBalance().intValue());
    Assert.assertEquals(fromAccount.get().getId(), transfer.get().getFrom().getId());
    Assert.assertEquals(toAccount.get().getId(), transfer.get().getTo().getId());
  }

  @Test(expected = NegativeBalanceException.class)
  public void testTransferMoneyWithError() {
    Optional<Account> fromAccount = accountRepository.findByName(FROM_NAME);
    Optional<Account> toAccount = accountRepository.findByName(TO_NAME);

    TransferRequest transferRequest = createTransferRequest(fromAccount.get(),
            toAccount.get(),
            BigDecimal.valueOf(55));
    transferService.transfer(transferRequest);
  }

  @After
  public void tearDown() {
    transferRepository.deleteAll();
    accountRepository.deleteAll();
  }

}
