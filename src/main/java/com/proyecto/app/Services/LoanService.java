package com.proyecto.app.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.app.Domain.Loan;
import com.proyecto.app.Repository.LoanRepository;
@Service
public class LoanService {
    private LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan searchLoanByIdOfUser(long id){
        Loan loan= new Loan();
        List<Loan> loans =new ArrayList<>();
        System.out.println("hola");
        loans.addAll(loanRepository.findAll());
        System.out.println("hola1");
        for (Loan loan2 : loans) {
            if (loan2.getUser().getId()==id) {
                loan=loan2;
                return loan;     
            }
            
        }
        return null;    
        
    }
    
}
