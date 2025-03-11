package org.kuraterut.FinancialAccountingHSE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class FinancialAccountingHSEApplication {
    public static void main(String[] args) {
//        log.info("FinancialAccountingHSEApplication started");
        SpringApplication.run(FinancialAccountingHSEApplication.class, args);
    }
}