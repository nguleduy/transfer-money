package com.example.joseph.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table
@NoArgsConstructor
public class Transfer implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private BigDecimal amount;

  @ManyToOne
  @JoinColumn(name = "from_id", referencedColumnName = "id")
  private Account from;

  @ManyToOne
  @JoinColumn(name = "to_id", referencedColumnName = "id")
  private Account to;

}
