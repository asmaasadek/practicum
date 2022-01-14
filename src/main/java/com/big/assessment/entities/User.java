package com.big.assessment.entities;

import com.big.assessment.models.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id", insertable = false, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq-gen")
    @SequenceGenerator(name = "seq-gen", sequenceName = "user_seq", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_mail", unique = true, nullable = false)
    private String userMail;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "virtual_currency")
    private Double virtualCurrency;

    public User fromUserDTO(UserDTO userDTO) {
        this.setUserMail(userDTO.getUserMail());
        this.setUserName(userDTO.getUserName());
        this.setUserPassword(userDTO.getUserPassword());
        return this;
    }
}
