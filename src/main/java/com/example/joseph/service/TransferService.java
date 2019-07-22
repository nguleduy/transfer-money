package com.example.joseph.service;

import com.example.joseph.dto.transfer.TransferRequest;
import com.example.joseph.dto.transfer.TransferResponse;
import com.example.joseph.entity.Account;
import com.example.joseph.entity.Transfer;
import com.example.joseph.exception.NegativeBalanceException;
import com.example.joseph.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferService {

  private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

  private final AccountService accountService;

  private final TransferRepository transferRepository;

  private final LockService lockService;

  public TransferResponse transfer(TransferRequest transferRequest) {
    try {
      lockService.lock();

      Account fromAccount = accountService.retrieve(transferRequest.getFromName());

      Account toAccount = accountService.retrieve(transferRequest.getToName());

      TransferResponse transferResponse = saveTransfer(fromAccount,
              toAccount,
              transferRequest.getAmount());
      logger.info("Transfer Successful From: {} To: {}", fromAccount.getName(), toAccount.getName());
      return transferResponse;
    } finally {
      lockService.unlock();
    }
  }

  @Transactional(rollbackFor = Exception.class, noRollbackFor = NegativeBalanceException.class)
  public TransferResponse saveTransfer(Account fromAccount,
                                       Account toAccount,
                                       final BigDecimal amount) {

    if (fromAccount.getBalance().compareTo(amount) < 0) {
      logger.info("{} Balance must be bigger than Amount", fromAccount.getName());
      throw new NegativeBalanceException(fromAccount.getName());
    }
    logger.info("Transfer Amount:{}, From: {}, To: {}",
            amount,
            fromAccount.getName(),
            toAccount.getName());

    fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
    accountService.save(fromAccount);

    toAccount.setBalance(toAccount.getBalance().add(amount));
    accountService.save(toAccount);

    Transfer transfer = new Transfer();
    transfer.setAmount(amount);
    transfer.setFrom(fromAccount);
    transfer.setTo(toAccount);
    transferRepository.save(transfer);

    return new TransferResponse(transfer.getId(),
            System.nanoTime(),
            amount,
            fromAccount.getName(),
            toAccount.getName());
  }

}
