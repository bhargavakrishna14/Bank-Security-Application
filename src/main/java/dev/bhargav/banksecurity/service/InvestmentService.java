package dev.bhargav.banksecurity.service;

import dev.bhargav.banksecurity.dto.InvestmentDto;
import dev.bhargav.banksecurity.entity.Account;
import dev.bhargav.banksecurity.entity.Investment;
import dev.bhargav.banksecurity.entity.InvestmentType;
import dev.bhargav.banksecurity.exceptions.AccountNotFoundException;
import dev.bhargav.banksecurity.exceptions.InsufficientBalanceException;
import dev.bhargav.banksecurity.exceptions.InvalidInvestmentTypeException;
import dev.bhargav.banksecurity.repository.AccountRepository;
import dev.bhargav.banksecurity.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvestmentService {

    private final AccountRepository accountRepository;

    private final InvestmentRepository investmentRepository;

    @Transactional
    public String investNow(Long accountId, InvestmentDto investmentDto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        // Validate balance
        if (account.getBalance() < investmentDto.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance for investment");
        }

        // Validate & set investment type
        Investment investment = new Investment();
        InvestmentType type;
        try {
            type = InvestmentType.valueOf(investmentDto.getInvestmentType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInvestmentTypeException("Invalid investment type: " + investmentDto.getInvestmentType());
        }

        switch (type) {
            case GOLD -> {
                investment.setRisk("Low");
                investment.setReturns(12F);
                investment.setCompanyName("BuyNow");
            }
            case STOCKS -> {
                investment.setRisk("High");
                investment.setReturns(20F);
                investment.setCompanyName("StockWay");
            }
            case MUTUAL_FUND -> {
                investment.setRisk("Moderate");
                investment.setReturns(12.3F);
                investment.setCompanyName("Ray Fund");
            }
            case FIXED_DEPOSITS -> {
                investment.setRisk("Low");
                investment.setReturns(9.2F);
                investment.setCompanyName("PST");
            }
        }

        // Deduct amount & save investment
        account.setBalance(account.getBalance() - investmentDto.getAmount());
        investment.setInvestmentType(type);
        investment.setAmount(investmentDto.getAmount());
        investment.setDuration(investmentDto.getDuration());
        investment.setUser(account.getUser());

        investmentRepository.save(investment);
        accountRepository.save(account);

        return "Investment successful in " + type.name();
    }

}
