package com.vicente.apirest.apirest.Controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicente.apirest.apirest.Entities.Book;
import com.vicente.apirest.apirest.Entities.Loan;
import com.vicente.apirest.apirest.Entities.User;
import com.vicente.apirest.apirest.Repositories.BookRepository;
import com.vicente.apirest.apirest.Repositories.LoanRepository;
import com.vicente.apirest.apirest.Repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/loans")
public class LoanController {

     @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable(value = "id") Long loanId) {
        return loanRepository.findById(loanId)
        .orElseThrow(() -> new RuntimeException("The loan with the ID: " + loanId + " was not found"));
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@Valid @RequestBody Loan loanRequest) {
        User user = userRepository.findById(loanRequest.getUser().getId()).orElse(null);
        Book book = bookRepository.findById(loanRequest.getBook().getId()).orElse(null);

        if (user == null || book == null) {
            return ResponseEntity.badRequest().build();
        }

        loanRequest.setUser(user);
        loanRequest.setBook(book);
        loanRequest.setLoanDate(LocalDate.now());

        Loan loan = loanRepository.save(loanRequest);
        return ResponseEntity.ok(loan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable(value = "id") Long loanId, @Valid @RequestBody Loan loanDetails) {
        Loan loan = loanRepository.findById(loanId).orElse(null);
        if (loan == null) {
            return ResponseEntity.notFound().build();
        }

        User user = userRepository.findById(loanDetails.getUser().getId()).orElse(null);
        Book book = bookRepository.findById(loanDetails.getBook().getId()).orElse(null);

        if (user == null || book == null) {
            return ResponseEntity.badRequest().build();
        }

        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(loanDetails.getLoanDate());
        loan.setReturnDate(loanDetails.getReturnDate());

        Loan updatedLoan = loanRepository.save(loan);
        return ResponseEntity.ok(updatedLoan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable(value = "id") Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElse(null);
        if (loan == null) {
            return ResponseEntity.notFound().build();
        }

        loanRepository.delete(loan);
        return ResponseEntity.ok().build();
    }

}
