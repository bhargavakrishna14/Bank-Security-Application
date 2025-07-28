package dev.bhargav.banksecurity.service;

import dev.bhargav.banksecurity.entity.*;
import dev.bhargav.banksecurity.repository.AccountRepository;
import dev.bhargav.banksecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserByName(String username) {
        return userRepository.findByUsername(username).
                orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public String deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found: " + userId);
        }
        userRepository.deleteById(userId);
        return "Deleted Successfully";
    }

    @Transactional
    public String deactivateUser(Long userId, Long accountId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountId));

        if (!user.getAccountList().contains(account)) {
            throw new RuntimeException("Account does not belong to this user");
        }
        account.setStatus(AccountStatus.INACTIVE);
        accountRepository.save(account);
        log.info("Deactivated account {} for user {}", accountId, userId);
        return "Deactivated account for user with id: " + userId;
    }

    @Transactional
    public String activateAccount(Long userId, Long accountId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountId));

        if (!user.getAccountList().contains(account)) {
            throw new RuntimeException("Account does not belong to this user");
        }
        if (account.getStatus() == AccountStatus.ACTIVE) {
            throw new RuntimeException("Account is already active");
        }
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        log.info("Activated account {} for user {}", accountId, userId);
        return "Activated account for user with id: " + userId;
    }

    public List<Account> getAllActiveAccountList() {
        return accountRepository.findAllActiveAccounts();
    }

    public List<Account> getAllInActiveAccountList() {
        return accountRepository.findAllInActiveAccounts();
    }

    public List<Account> byAccType(AccountType accType) {
        return accountRepository.findAllByAccountType(accType);
    }

    public List<Account> byBranchType(BranchType branchType) {
        return accountRepository.findAllByBranch(branchType);
    }
}
