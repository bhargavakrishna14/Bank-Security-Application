package dev.bhargav.banksecurity.investments;

import dev.bhargav.banksecurity.dto.InvestmentDto;
import dev.bhargav.banksecurity.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invest")
public class UserInvestmentController {
    @Autowired
    InvestmentService investmentService;
    @PostMapping("/now")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public String investNow(@RequestParam Long accountId , @RequestBody InvestmentDto investmentDto)
    {
        return investmentService.investNow(accountId,investmentDto);
    }


}
