package com.example.joseph.exception;

import lombok.Getter;

@Getter
public class NegativeBalanceException extends RuntimeException {

  private final String name;

  public NegativeBalanceException(String name) {
    super(name + " balance must not be negative!");
    this.name = name;
  }

}
