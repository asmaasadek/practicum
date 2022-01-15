package com.big.assessment.repositories;

import com.big.assessment.entities.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Integer> {

    List<UserTransaction> findAllByFromUserOrToUser(Integer fromUser, Integer toUser);
}
