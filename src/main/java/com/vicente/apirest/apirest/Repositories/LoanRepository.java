package com.vicente.apirest.apirest.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vicente.apirest.apirest.Entities.Loan;

public interface LoanRepository extends JpaRepository<Loan,Long>{

}
