package com.example.joseph.service;

import com.example.joseph.dto.transfer.TransferRequest;
import com.example.joseph.dto.transfer.TransferResponse;
import com.example.joseph.entity.Account;
import com.example.joseph.repository.TransferRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {

  @InjectMocks
  private TransferService transferService;

  @Mock
  private TransferRepository transferRepository;

  @Mock
  private AccountService accountService;

  @Mock
  private LockService lockService;

  @Test
  public void testTransferMoney() {
    String fromAccountName = "fromAccount";
    String toAccountName = "toAccount";

    TransferRequest transferRequest = new TransferRequest();
    transferRequest.setFromName(fromAccountName);
    transferRequest.setToName(toAccountName);
    transferRequest.setAmount(BigDecimal.TEN);

    Account fromAccount = new Account(fromAccountName);
    fromAccount.setBalance(BigDecimal.valueOf(50));

    Account toAccount = new Account(toAccountName);

    Mockito.when(accountService.retrieve(fromAccountName)).thenReturn(fromAccount);
    Mockito.when(accountService.retrieve(toAccountName)).thenReturn(toAccount);

    TransferResponse transferResponse = transferService.transfer(transferRequest);

    Assert.assertNotNull(transferResponse);
    Assert.assertNotNull(transferResponse.getResult());
    Assert.assertEquals(0, transferResponse.getResult().getCode().intValue());
    Assert.assertEquals(BigDecimal.TEN, transferResponse.getAmount());
    Assert.assertEquals(fromAccountName, transferResponse.getFromName());
    Assert.assertEquals(toAccountName, transferResponse.getToName());
  }

}
