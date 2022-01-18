package com.big.assessment.repositories;

import com.big.assessment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserMail(String userMail);

    @Query("UPDATE User u SET u.virtualCurrency = (u.virtualCurrency + :value)")
    @Transactional
    @Modifying
    void incrementAllUsersVC(double value);

    @Query("UPDATE User u SET u.virtualCurrency = (u.virtualCurrency + :amount) where id= :userId")
    @Transactional
    @Modifying
    void updateUserVC(Integer userId, Double amount);


}
