package dev.bhargav.banksecurity.controller;

import dev.bhargav.banksecurity.dto.CardDto;
import dev.bhargav.banksecurity.entity.Card;
import dev.bhargav.banksecurity.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/card")
@RequiredArgsConstructor
public class UserCardController {

    private final CardService cardService;

    @GetMapping("/block")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public String blockCard(
            @RequestParam Long accountNumber,
            @RequestParam Long cardNumber) {
        return cardService.blockCard(accountNumber,cardNumber);
    }

    @PostMapping("/apply/new")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public String applyNewCard(
            @RequestParam Long accountNumber,
            @RequestBody CardDto cardDto) {
        return cardService.applyNewCard(accountNumber,cardDto);
    }

    @PutMapping("/setting")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public void modifyLimit(
            @RequestBody Card card,
            @RequestParam Long cardNumber) {
        cardService.modifySetting(cardNumber,card);
    }

}


