package com.example.joseph.api;

import com.example.joseph.dto.account.AccountRequest;
import com.example.joseph.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/accounts")
public class AccountController {

  private final AccountService accountService;

  @PostMapping(value = "/create")
  public ResponseEntity<?> createAccount(@RequestBody @Valid AccountRequest accountRequest) {
    return ResponseEntity.ok(accountService.createAccount(accountRequest));
  }

}
