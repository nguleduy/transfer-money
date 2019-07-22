package com.example.joseph.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

  @JsonProperty(value = "code")
  private Integer code;

  @JsonProperty(value = "message")
  private String message;

}
