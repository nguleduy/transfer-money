package com.example.joseph.exception;

public class AccountNotFoundException extends RuntimeException {

  private final String name;

  public AccountNotFoundException(String name) {
    super("Account Not Found With Name: " + name);
    this.name = name;
  }

}
