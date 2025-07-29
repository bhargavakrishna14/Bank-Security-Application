package dev.bhargav.banksecurity.service;

import dev.bhargav.banksecurity.dto.CardDto;
import dev.bhargav.banksecurity.entity.Account;
import dev.bhargav.banksecurity.entity.Card;
import dev.bhargav.banksecurity.entity.CardStatus;
import dev.bhargav.banksecurity.entity.CardType;
import dev.bhargav.banksecurity.exceptions.*;
import dev.bhargav.banksecurity.repository.AccountRepository;
import dev.bhargav.banksecurity.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CardService {

    private final AccountRepository accountRepository;

    private final CardRepository cardRepository;

    /**
     * Blocks a card and unlinks it from the account.
     */
    public String blockCard(Long accountNumber, Long cardNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new CardNotFoundException(cardNumber));

        if (!Objects.equals(account.getCard().getCardNumber(), cardNumber)) {
            throw new CardNotFoundException(cardNumber);
        }

        card.setStatus(CardStatus.BLOCKED);
        cardRepository.save(card);
        account.setCard(null);
        accountRepository.save(account);

        return "Card blocked successfully.";
    }

    /**
     * Allows a user to apply for a new card (if no active card is present).
     */
    public String applyNewCard(Long accountNumber, CardDto cardDto) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        if (account.getCard() != null
                && account.getCard().getCardNumber() != null) {
            throw new CardAlreadyExistsException(accountNumber);
        }

        CardType type = parseCardType(cardDto.getCardType());
        Card newCard = buildCard(type, cardDto.getCardHolderName(), cardDto.getPin());
        Card savedCard = cardRepository.save(newCard);

        account.setCard(savedCard);
        accountRepository.save(account);

        return "New " + type + " card allocated to account: " + accountNumber;
    }

    /**
     * Updates card settings such as daily limit & PIN.
     */
    public void modifySetting(Long cardNumber, Card cardDto) {
        Card fetchedCard = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new CardNotFoundException(cardNumber));

        if (cardDto.getDailyLimit() != 0) {
            validateLimit(fetchedCard.getCardType(), cardDto.getDailyLimit());
            fetchedCard.setDailyLimit(cardDto.getDailyLimit());
        }
        if (cardDto.getPin() != null) {
            fetchedCard.setPin(cardDto.getPin());
        }

        cardRepository.save(fetchedCard);
    }

    /**
     * Creates a new card for account creation based on account type.
     * Returns null if account type does not require a card (e.g., PPF).
     */
    public Card createCardForAccount(String accountType, String holderName, Long defaultPin) {
        accountType = accountType.toUpperCase();

        if ("PPF".equals(accountType)) {
            return null; // PPF does not get a card
        }

        CardType cardType = switch (accountType) {
            case "SAVINGS" -> CardType.DEBIT_GLOBAL;
            case "CURRENT" -> CardType.CREDIT_PREMIUM;
            case "SALARY" -> CardType.CREDIT_MASTER;
            default -> throw new InvalidCardTypeException(accountType);
        };

        return cardRepository.save(buildCard(cardType, holderName, defaultPin));
    }

    // === PRIVATE HELPERS ===

    private Card buildCard(CardType type, String holderName, Long pin) {
        Card card = new Card();
        card.setCardNumber(generateUniqueCardNumber());
        card.setCvv(generateCvv());
        card.setCardType(type);
        card.setDailyLimit(getDefaultLimit(type));
        card.setPin(pin);
        card.setAllocationDate(new Date());
        card.setExpiryDate(generateExpiryDate());
        card.setCardHolderName(holderName);
        card.setStatus(CardStatus.ACTIVE);
        return card;
    }

    private CardType parseCardType(String type) {
        try {
            return CardType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCardTypeException(type);
        }
    }

    private double getDefaultLimit(CardType type) {
        return switch (type) {
            case DEBIT_CLASSIC -> 20000;
            case DEBIT_GLOBAL -> 40000;
            case CREDIT_PREMIUM -> 50000;
            case CREDIT_MASTER -> 75000;
        };
    }

    private void validateLimit(CardType type, Double limit) {
        if (limit == null || limit <= 0) {
            throw new InvalidCardLimitException(limit);
        }
        double max = switch (type) {
            case DEBIT_CLASSIC -> 40000;
            case DEBIT_GLOBAL -> 50000;
            case CREDIT_PREMIUM -> 75000;
            case CREDIT_MASTER -> 100000;
        };
        if (limit > max) {
            throw new InvalidCardLimitException(limit);
        }
    }

    private int generateCvv() {
        return ThreadLocalRandom.current().nextInt(100, 1000); // 3-digit CVV
    }

    private Long generateUniqueCardNumber() {
        Long cardNumber;
        do {
            cardNumber = generate16DigitNumber();
        } while (cardRepository.findByCardNumber(cardNumber).isPresent());
        return cardNumber;
    }

    private long generate16DigitNumber() {
        long number = 0L;
        for (int i = 0; i < 16; i++) {
            number = number * 10 + ThreadLocalRandom.current().nextInt(0, 10);
        }
        return number;
    }

    private Date generateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 5);
        return calendar.getTime();
    }
}
