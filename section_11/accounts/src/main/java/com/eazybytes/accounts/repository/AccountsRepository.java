package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByCustomerId(Long customerId);

//    Whenever you update or delete data in the database with custom query methods,
//    you need to use 2 annotations:
//    @Transactional and @Modifying annotation

    @Transactional //(If error happen then rollback)
    @Modifying
    void deleteByCustomerId(Long customerId);

}