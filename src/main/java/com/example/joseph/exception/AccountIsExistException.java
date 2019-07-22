package com.example.joseph.exception;

public class AccountIsExistException extends RuntimeException {

  private final String name;

  public AccountIsExistException(String name) {
    super("Account Is Exist With Name: " + name);
    this.name = name;
  }

}
