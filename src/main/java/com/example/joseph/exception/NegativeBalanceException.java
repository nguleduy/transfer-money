package com.example.joseph.exception;

public class NegativeBalanceException extends RuntimeException {

  private final String name;

  public NegativeBalanceException(String name) {
    super(name + " balance must not be negative!");
    this.name = name;
  }

}
