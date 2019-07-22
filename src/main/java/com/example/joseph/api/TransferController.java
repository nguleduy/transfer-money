package com.example.joseph.api;

import com.example.joseph.dto.transfer.TransferRequest;
import com.example.joseph.dto.transfer.TransferResponse;
import com.example.joseph.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/transfers")
public class TransferController {

  private final TransferService transferService;

  @PostMapping()
  public ResponseEntity<TransferResponse> transfer(@RequestBody @Valid TransferRequest transferRequest) {
    return ResponseEntity.ok(transferService.transfer(transferRequest));
  }

}
