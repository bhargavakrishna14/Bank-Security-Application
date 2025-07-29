package dev.bhargav.banksecurity.controller;

import dev.bhargav.banksecurity.dto.InvestmentDto;
import dev.bhargav.banksecurity.service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invest")
@RequiredArgsConstructor
public class UserInvestmentController {

    private final InvestmentService investmentService;

    @PostMapping("/now")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public String investNow(
            @RequestParam Long accountId ,
            @RequestBody InvestmentDto investmentDto) {
        return investmentService.investNow(accountId,investmentDto);
    }

}
