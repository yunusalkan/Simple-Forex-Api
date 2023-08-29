package com.yns.forex.simpleforexapi.persistence;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "CONVERSION"
, indexes = {
        @Index(name = "idx_transaction_id", columnList = "TRANSACTION_ID"),
        @Index(name = "idx_transaction_date", columnList = "TRANSACTION_DATE")
})
public class ConversionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "TRANSACTION_DATE")
    private LocalDate transactionDate;

    @Column(name = "SOURCE_CURRENCY")
    private String sourceCurrency;

    @Column(name = "TARGET_CURRENCY")
    private String targetCurrency;

    @Column(name = "SOURCE_AMOUNT", columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal sourceAmount;

    @Column(name = "TARGET_AMOUNT", columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal targetAmount;
}
