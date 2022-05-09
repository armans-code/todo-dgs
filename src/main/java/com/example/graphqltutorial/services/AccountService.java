package com.example.graphqltutorial.services;

import com.example.graphqltutorial.entities.AccountEntity;
import com.example.graphqltutorial.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountEntity> getAccounts() {
        return accountRepository.findAll();
    }

    public AccountEntity addAccount(AccountEntity account) {
        return accountRepository.save(account);
    }

    @Transactional
    public AccountEntity updateAccountName(UUID account_id, String name) {
        AccountEntity account = accountRepository.findById(account_id).orElseThrow(() -> new IllegalStateException(
                "account with id " + account_id + " not found"
        ));
        account.setName(name);
        return account;
    }

    public AccountEntity deleteAccount(UUID account_id) {
        AccountEntity account = accountRepository.findById(account_id).orElseThrow(() -> new IllegalStateException(
                "account with id " + account_id + " not found"
        ));
        accountRepository.delete(account);
        return account;
    }
}
