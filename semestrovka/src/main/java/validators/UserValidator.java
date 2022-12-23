package validators;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import models.Account;
import repositories.AccountRepository;
import repositories.AccountRepositoryImpl;
import utils.HashUtil;
import utils.PostgresConnectionProvider;

import java.util.List;

public class UserValidator {

    public final AccountRepository accountRepository = new AccountRepositoryImpl(PostgresConnectionProvider.getConnection());

    public boolean userNameInBase (String username) {
        List<String> accounts = accountRepository.findAll().stream().map(Account::getUsername).toList();
        return accounts.contains(username);
    }

    public boolean userInBase(Account account, HttpServletRequest request) {
        List<Account> accounts = accountRepository.findAll();
        if (userNameInBase(account.getUsername())) {
            if (account.getPassword()
                    .equals(accountRepository.getPasswordByName(account.getUsername()))){

                for (Account acc: accounts) {
                    if (acc.getUsername().equals(account.getUsername())) {
                        account.setId(acc.getId());
                    }
                }

                if (!(accountRepository.isDeleted(account.getUsername()))) {
                    return true;
                }
            }
        }
        request.setAttribute("incorrect", "error");
        return false;
    }


}
