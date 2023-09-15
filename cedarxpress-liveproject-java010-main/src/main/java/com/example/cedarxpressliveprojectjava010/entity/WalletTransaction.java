package com.example.cedarxpressliveprojectjava010.entity;

import com.example.cedarxpressliveprojectjava010.enums.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WalletTransaction extends Base{

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull(message = "amount field is empty")
    private BigDecimal amount;

    @ManyToOne(cascade = CascadeType.ALL)
    private Wallet wallet;
}
