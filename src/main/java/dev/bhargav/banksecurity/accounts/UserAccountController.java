package dev.bhargav.banksecurity.accounts;

import dev.bhargav.banksecurity.dto.AccountDto;
import dev.bhargav.banksecurity.dto.KycDto;
import dev.bhargav.banksecurity.dto.NomineeDto;
import dev.bhargav.banksecurity.entity.Account;
import dev.bhargav.banksecurity.entity.Nominee;
import dev.bhargav.banksecurity.entity.User;
import dev.bhargav.banksecurity.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {

    private final AccountService accountService;

    @PostMapping("/create/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
    public void accountOpening(
            @RequestBody AccountDto accountDto,
            @PathVariable Long userId) {
        accountService.createAccount(accountDto,userId);
    }

    @GetMapping("/all/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<Account> getAllAccountByUserId(
            @PathVariable Long userId) {
        return accountService.getAllAccountById(userId);
    }

    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public double getBalance(@RequestParam Long accountNumber) {
        return accountService.getBalanceAmount(accountNumber);
    }

    @GetMapping("/nominee")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public Nominee getNominee(@RequestParam Long accountNumber) {
        return accountService.getNominee(accountNumber);
    }

    @PutMapping("/updateNominee/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public void updateNominee(
            @RequestBody NomineeDto nomineeDto,
            @PathVariable Long accountId) {
        accountService.updateNominee(nomineeDto,accountId);
    }

    @GetMapping("/getKycDetails")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public User getKycDetails(@RequestParam Long accountNumber) {
        return accountService.getAccountKycDetail(accountNumber);
    }

    @PutMapping("/updateKyc/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public void updateKycDetails(
            @RequestBody KycDto kycDto,
            @PathVariable Long accountId) {
        accountService.updateKycDetails(kycDto,accountId);
    }

    @GetMapping("/getAccount/summary")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public Account getAccountSummary(@RequestParam Long accountNumber) {
        return accountService.getAccountDetail(accountNumber);
    }

}
