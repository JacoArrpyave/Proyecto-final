package com.proyecto.app.Domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_LOAN")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_id")
    private List<Book> books;

    @Column(name = "books_quantity")
    private List<Integer>  books_quantity;

    public Loan() {
    }

    public Loan( User user, List<Book> books, List<Integer> books_quantity) {
        
        this.user = user;
        this.books = books;
        this.books_quantity = books_quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Integer> getBooks_quantity() {
        return books_quantity;
    }

    public void setBooks_quantity(List<Integer> books_quantity) {
        this.books_quantity = books_quantity;
    }

}
