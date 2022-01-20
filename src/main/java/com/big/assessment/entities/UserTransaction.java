package com.big.assessment.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "users_transactions")
@Data
@NoArgsConstructor
public class UserTransaction {

    @Id
    @Column(name = "id", insertable = false, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction-seq-gen")
    @SequenceGenerator(name = "transaction-seq-gen", sequenceName = "transaction_seq", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "from_user", nullable = false)
    private Integer fromUser;

    @Column(name = "to_user", nullable = false)
    private Integer toUser;

    @Column(name = "transaction_amount", nullable = false)
    private Double transactionAmount;

    @Column(name = "transaction_date", nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Generated(value = GenerationTime.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date transactionDate;

    public UserTransaction registerTransaction(Integer fromUser, Integer toUser, Double amount) {
        this.setFromUser(fromUser);
        this.setToUser(toUser);
        this.setTransactionAmount(amount);
        return this;
    }
}
