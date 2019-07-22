package com.example.joseph.dto.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class TransferRequest {

  @NotBlank(message = "From Name must not be empty.")
  @JsonProperty(value = "from_name")
  private String fromName;

  @NotBlank(message = "To Name must not be empty.")
  @JsonProperty(value = "to_name")
  private String toName;

  @JsonProperty(value = "amount")
  @DecimalMin("1")
  private BigDecimal amount;

}
