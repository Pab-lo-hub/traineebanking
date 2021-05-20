package com.backend.interfaces;

import com.backend.classes.Account;
import com.backend.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUser(@Param("User") User user);
}
