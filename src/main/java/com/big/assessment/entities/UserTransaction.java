package com.big.assessment.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users_transactions")
@Data
@NoArgsConstructor
public class UserTransaction {

    @Id
    @Column(name = "id", insertable = false, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq-gen")
    @SequenceGenerator(name = "seq-gen", sequenceName = "transaction_seq", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "from_user", nullable = false)
    private Integer fromUser;

    @Column(name = "to_user", nullable = false)
    private Integer toUser;

    @Column(name = "transaction_amount", nullable = false)
    private Double transactionAmount;

    @Column(name = "transaction_date", nullable = false, insertable = false, updatable = false)
    private Timestamp transactionDate;

    public UserTransaction registerTransaction(Integer fromUser, Integer toUser) {
        this.setFromUser(fromUser);
        this.setToUser(toUser);
        return this;
    }
}
