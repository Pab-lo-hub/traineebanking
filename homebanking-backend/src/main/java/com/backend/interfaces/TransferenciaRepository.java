package com.backend.interfaces;

import com.backend.classes.Account;
import com.backend.classes.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TransferenciaRepository extends JpaRepository<Transferencia, Integer> {
}

