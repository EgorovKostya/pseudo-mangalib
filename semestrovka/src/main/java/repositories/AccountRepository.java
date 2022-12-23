package repositories;

import models.Account;

public interface AccountRepository extends Crud<Account, Long>{
    String getPasswordByName(String username);

    Boolean isDeleted(String username);
}
