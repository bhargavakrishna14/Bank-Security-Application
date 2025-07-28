package dev.bhargav.banksecurity.admin;

import dev.bhargav.banksecurity.dto.AdminDto;
import dev.bhargav.banksecurity.entity.Account;
import dev.bhargav.banksecurity.entity.AccountType;
import dev.bhargav.banksecurity.entity.BranchType;
import dev.bhargav.banksecurity.entity.User;
import dev.bhargav.banksecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService adminUserService;

    @GetMapping("/getAllUser")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        return adminUserService.getAllUsers(PageRequest.of(page, size));
    }

    @GetMapping("/getUserByName/{username}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserByName(@PathVariable String username) {
        return adminUserService.getUserByName(username);
    }

    @DeleteMapping("/deleteUser/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUserById(@PathVariable Long userId) {
        return adminUserService.deleteUserById(userId);
    }

    @PutMapping("/account/deactivate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ADMIN')")
    public String deactivateAccount(
            @RequestParam Long userId,
            @RequestParam Long accountId) {
        return adminUserService.deactivateUser(userId,accountId);
    }

    @PutMapping("/account/activate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ADMIN')")
    public String activateAccount(
            @RequestParam Long userId,
            @RequestParam Long accountId) {
        return adminUserService.activateAccount(userId,accountId);
    }

    @GetMapping("/account/getActiveAccountsList")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<Account> activeAccountList() {
       return adminUserService.getAllActiveAccountList();
    }

    @GetMapping("/account/getInActiveAccountsList")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<Account> inActiveAccountList() {
        return adminUserService.getAllInActiveAccountList();
    }

    @GetMapping("/accountList/ByAccountType/{accType}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<Account> getAccountListByAccType(
            @PathVariable AccountType accType) {
        return adminUserService.byAccType(accType);
    }

    @GetMapping("/accountList/ByBranchType/{branchType}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<Account> getAccountListByBranchType(
            @PathVariable BranchType branchType) {
        return adminUserService.byBranchType(branchType);
    }

}
