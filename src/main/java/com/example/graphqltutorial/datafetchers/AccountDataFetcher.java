package com.example.graphqltutorial.datafetchers;

import com.example.graphqltutorial.entities.AccountEntity;
import com.example.graphqltutorial.generated.types.Account;
import com.example.graphqltutorial.generated.types.AccountInput;
import com.example.graphqltutorial.services.AccountService;
import com.netflix.graphql.dgs.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DgsComponent
public class AccountDataFetcher {
    private final AccountService accountService;

    public AccountDataFetcher(AccountService accountService) {
        this.accountService = accountService;
    }

    @DgsQuery
    public List<Account> account() {
        return accountService.getAccounts().stream().map(AccountDataFetcher::buildAccount).collect(Collectors.toList());
    }

    @DgsMutation
    public Account addAccount(@InputArgument AccountInput accountInput) {
        AccountEntity account = accountService.addAccount(new AccountEntity(accountInput.getName()));
        return buildAccount(account);
    }

    @DgsMutation
    public Account updateAccountName(@InputArgument AccountInput accountInput) {
        return buildAccount(accountService.updateAccountName(
                UUID.fromString(accountInput.getAccount_id()),
                accountInput.getName()
        ));
    }

    @DgsMutation
    public Account deleteAccount(@InputArgument AccountInput accountInput) {
        return buildAccount(accountService.deleteAccount(UUID.fromString(accountInput.getAccount_id())));
    }

    private static Account buildAccount(AccountEntity accountEntity) {
        return Account.newBuilder()
                .account_id(accountEntity.getAccount_id().toString())
                .name(accountEntity.getName())
                .build();
    }
}
