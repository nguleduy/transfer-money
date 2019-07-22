package com.example.joseph.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AccountRequest {

  @JsonProperty(value = "name")
  @NotBlank(message = "Account name must not be empty.")
  private String name;

}
