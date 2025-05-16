package dev.bhargav.banksecurity.repository;

import dev.bhargav.banksecurity.entity.Account;
import dev.bhargav.banksecurity.entity.AccountType;
import dev.bhargav.banksecurity.entity.BranchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    //  Derived Query to fetch all accounts by accountNumber.
    Optional<Account> findByAccountNumber(Long accountNumber);

    //  JPQL for fetching active accounts from the database.
    @Query("SELECT a FROM Account a where a.status = 'ACTIVE'")
    List<Account> findAllActiveAccounts();

    //  JPQL for fetching inactive accounts from the database.
    @Query("SELECT a FROM Account a WHERE a.status = 'INACTIVE'")
    List<Account> findAllInActiveAccounts();

    //    JPQL for fetching accounts by the given accountType from the database.
    @Query("SELECT a FROM Account a WHERE a.accountType = :accountType")
    List<Account> findAllByAccountType(AccountType accountType);

    //    JPQL for fetching accounts by the given branchType from the database.
    @Query("SELECT a FROM Account a WHERE a.branch = :branchType")
    List<Account> findAllByBranch(BranchType branchType);

}
