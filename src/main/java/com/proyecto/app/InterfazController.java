package com.proyecto.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.proyecto.app.Domain.Book;
import com.proyecto.app.Domain.User;
import com.proyecto.app.Repository.BookRepository;
import com.proyecto.app.Repository.LoanRepository;
import com.proyecto.app.Repository.UserRepository;
import org.springframework.ui.Model;

@Controller
public class InterfazController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    LoanRepository loanRepository;

    @GetMapping("/new-administrator")
    public String addAdministrator(Model model) {
        model.addAttribute("administrator", new User());
        return "add_administrator";
    }

    @PostMapping("/new-administrator")
    public String administratorSave(User user) {

        if (user.getRole().toLowerCase().equals("administrador")) {
            userRepository.save(user);
            return "create";

        } else {
            return "not_found";
        }

    }

    /* view ready */
    @GetMapping("/Users/{idAmistator}")
    public String getAllUsers(@PathVariable("idAmistator") long idAmistator, Model model) {
        Optional<User> user = userRepository.findById(idAmistator);
        List<User> users = new ArrayList<>();
        if (user.isPresent() && user.get().getRole().toLowerCase().equals("administrador")) {
            users.addAll(userRepository.findAll());
            model.addAttribute("userList", users);

            return "users";

        } else {
            return "not_found";
        }

    }

    @GetMapping("/new-book")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "add_book";
    }

    @PostMapping("/new-book")
    public String saveBook(Book book) {
        bookRepository.save(book);
        return "redirect:/Books";

    }

    @GetMapping("/Books")
    public String getAllBooks(Model model) {
        List<Book> books = new ArrayList<>();
        books.addAll(bookRepository.findAll());
        model.addAttribute("bookList", books);
        return "books";

    }



    @PostMapping("/new-user")
    public String userSave(User user) {
        
            if (user.getRole().toLowerCase().equals("usuario")) {
               userRepository.save(user);

                return "create";

            } else {
                return "not_found";
            }

        
    }
    @GetMapping("/new-user")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add_user";
    }

}
