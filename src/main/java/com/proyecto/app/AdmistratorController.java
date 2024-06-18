package com.proyecto.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.app.Domain.Book;
import com.proyecto.app.Domain.User;
import com.proyecto.app.Repository.BookRepository;
import com.proyecto.app.Repository.LoanRepository;
import com.proyecto.app.Repository.UserRepository;
import com.proyecto.app.Services.BookService;
import com.proyecto.app.Services.LoanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class AdmistratorController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    LoanRepository loanRepository;

    BookService bookService = new BookService(bookRepository);
    LoanService loanService = new LoanService(loanRepository);

    @PostMapping("/administrator")
    public ResponseEntity<User> saveAdministrator(@RequestBody User user) {
        try {
            if (user.getRole().toLowerCase().equals("administrador")) {
                User _user = userRepository.save(user);
                return new ResponseEntity<>(_user, HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/book")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        try {
            Book _book = bookRepository.save(book);
            return new ResponseEntity<>(_book, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("book/{id}")
    public ResponseEntity<Book> modifyBook(@RequestBody Book book, @PathVariable("id") long id) {
        try {
            Optional<Book> bookData = bookRepository.findById(id);
            if (bookData.isPresent()) {
                Book _book = bookData.get();
                _book.setTitle(book.getTitle());
                _book.setAuthor(book.getAuthor());
                _book.setGenre(book.getGenre());
                _book.setPublication_date(book.getPublication_date());
                _book.setIsbn(book.getIsbn());
                _book.setAvailable_quantity(book.getAvailable_quantity());
                _book.setDescription(book.getDescription());
                _book.setPicture(book.getPicture());
                return new ResponseEntity<>(bookRepository.save(_book), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

            }
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/book/{id}")
    public ResponseEntity<HttpStatus> deleteBook (@PathVariable("id") long id){
        try {
            bookRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        
    }
    @GetMapping("/users/{idAmistator}")
    public ResponseEntity<List<User>> getAllUsers(@PathVariable("idAmistator") long idAmistator) {
        try {
            Optional <User> user =userRepository.findById(idAmistator);
            List<User> users=new ArrayList<>();
            if (user.isPresent() && user.get().getRole().toLowerCase().equals("administrador")) {
                users.addAll(userRepository.findAll());
                return new ResponseEntity<>(users,HttpStatus.OK);
                
            }else{
                return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
            }
            
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);


        }
    }
    
    
    
}
