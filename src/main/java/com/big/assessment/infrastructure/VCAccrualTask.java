package com.big.assessment.infrastructure;

import com.big.assessment.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class VCAccrualTask {

    @Autowired
    private UserRepository userRepository;

    @Value("${accrue.value}")
    private double accrueValue;

    @Scheduled(fixedRateString = "${schedule.task.rate}")
    public void start() {
        log.info("Starting VC Incremental for all users with: " + accrueValue);
        userRepository.incrementUserVC(accrueValue);
    }
}

