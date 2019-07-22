package com.example.joseph.dto.transfer;

import com.example.joseph.dto.common.Response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse extends Response {

  @JsonProperty(value = "id")
  private Long id;

  private Long created;

  @JsonProperty(value = "amount")
  private BigDecimal amount;

  @JsonProperty(value = "from_name")
  private String fromName;

  @JsonProperty(value = "to_name")
  private String toName;

}
