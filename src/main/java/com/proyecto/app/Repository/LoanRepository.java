package com.proyecto.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.app.Domain.Loan;

public interface LoanRepository extends JpaRepository<Loan,Long > {
    
}
