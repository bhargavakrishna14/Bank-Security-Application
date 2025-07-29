package dev.bhargav.banksecurity.service;

import dev.bhargav.banksecurity.dto.AccountDto;
import dev.bhargav.banksecurity.dto.KycDto;
import dev.bhargav.banksecurity.dto.NomineeDto;
import dev.bhargav.banksecurity.entity.*;
import dev.bhargav.banksecurity.exceptions.AccountNotFoundException;
import dev.bhargav.banksecurity.exceptions.UserNotFoundException;
import dev.bhargav.banksecurity.repository.AccountRepository;
import dev.bhargav.banksecurity.repository.NomineeRepository;
import dev.bhargav.banksecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final NomineeRepository nomineeRepository;
    private final UserRepository userRepository;
    private final CardService cardService;

    /**
     * Creates a new account for a user and associates a card (if applicable).
     */
    public void createAccount(AccountDto accountDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Account account = new Account();
        account.setAccountNumber(generateUniqueAccountNumber());
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(accountDto.getBalance());
        account.setProof(accountDto.getProof());
        account.setOpeningDate(new Date());
        account.setUser(user);

        // Save nominee
        Nominee nominee = nomineeRepository.save(accountDto.getNominee());
        account.setNominee(nominee);

        // Assign card (if applicable)
        Card card = cardService.createCardForAccount(accountDto.getAccountType(), user.getName(), 1122L);
        account.setCard(card);

        // Set account type, branch & interest rate
        configureAccountType(account, accountDto.getAccountType());

        accountRepository.save(account);
    }

    public List<Account> getAllAccountById(Long userId) {
        User fetchedUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return fetchedUser.getAccountList();
    }

    public double getBalanceAmount(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
        return account.getBalance();
    }

    public Nominee getNominee(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
        return account.getNominee();
    }

    public User getAccountKycDetail(Long accountNumber) {
        Account fetchedAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
        User user = fetchedAccount.getUser();
        user.setInvestmentList(null);
        user.setAccountList(null);
        return user;
    }

    public Account getAccountDetail(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
        account.setUser(null);
        return account;
    }

    public void updateNominee(NomineeDto nomineeDto, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        Nominee nominee = account.getNominee();
        nominee.setName(nomineeDto.getName());
        nominee.setAccountNumber(nomineeDto.getAccountNumber());
        nominee.setRelation(nomineeDto.getRelation());
        nominee.setAge(nomineeDto.getAge());
        nominee.setGender(nomineeDto.getGender());
        nomineeRepository.save(nominee);
    }

    public void updateKycDetails(KycDto kycDto, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        User user = account.getUser();

        if (kycDto.getName() != null && !kycDto.getName().isEmpty()) user.setName(kycDto.getName());
        if (kycDto.getAddress() != null && !kycDto.getAddress().isEmpty()) user.setAddress(kycDto.getAddress());
        if (kycDto.getNumber() != null) user.setNumber(kycDto.getNumber());
        if (kycDto.getIdentityProof() != null && !kycDto.getIdentityProof().isEmpty())
            user.setIdentityProof(kycDto.getIdentityProof());

        userRepository.save(user);
    }

    // === PRIVATE HELPERS ===

    private Long generateUniqueAccountNumber() {
        Long number;
        do {
            number = ThreadLocalRandom.current().nextLong(10000000L, 99999999L);
        } while (accountRepository.findByAccountNumber(number).isPresent());
        return number;
    }

    private void configureAccountType(Account account, String accountType) {
        switch (accountType.toUpperCase()) {
            case "SAVINGS" -> {
                account.setAccountType(AccountType.SAVINGS);
                account.setBranch(BranchType.BOB);
                account.setInterestRate(2.70F);
            }
            case "CURRENT" -> {
                account.setAccountType(AccountType.CURRENT);
                account.setBranch(BranchType.ICIC);
                account.setInterestRate(5.2F);
            }
            case "SALARY" -> {
                account.setAccountType(AccountType.SALARY);
                account.setBranch(BranchType.HDFC);
                account.setInterestRate(4.1F);
            }
            case "PPF" -> {
                account.setAccountType(AccountType.PPF);
                account.setBranch(BranchType.SBI);
                account.setInterestRate(7.4F);
            }
            default -> throw new RuntimeException("Invalid account type: " + accountType);
        }
    }
}
