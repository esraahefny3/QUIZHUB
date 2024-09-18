package com.toptal.quizhub.app.configurations.persistence;

import com.toptal.quizhub.persistence.api.services.TransactionService;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Configuration
public class TransactionConfiguration {

    @Bean
    @Primary
    public JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    TransactionService transactionService() {

        return new TransactionServiceImpl();
    }

    static class TransactionServiceImpl implements TransactionService {

        @Transactional
        @Override
        public <T> T get(Supplier<T> supplier) {

            return supplier.get();
        }

        @Transactional(propagation = Propagation.REQUIRES_NEW)
        @Override
        public <T> T getInNewTransaction(Supplier<T> supplier) {

            return supplier.get();
        }

        @Transactional
        @Override
        public void run(Runnable runnable) {

            runnable.run();
        }

        @Transactional(propagation = Propagation.REQUIRES_NEW)
        @Override
        public void runInNewTransaction(Runnable runnable) {

            runnable.run();
        }
    }
}
