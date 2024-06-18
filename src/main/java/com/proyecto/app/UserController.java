package com.proyecto.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.app.Domain.Book;
import com.proyecto.app.Domain.Loan;
import com.proyecto.app.Domain.User;
import com.proyecto.app.Repository.BookRepository;
import com.proyecto.app.Repository.LoanRepository;
import com.proyecto.app.Repository.UserRepository;
import com.proyecto.app.Services.BookService;
import com.proyecto.app.Services.LoanService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    LoanRepository loanRepository;


    @PostMapping("/loan/{bookTitle}/{id}/{available_quantity}")
    public ResponseEntity<Loan> creatloan(@PathVariable("bookTitle") String bookTitle, @PathVariable("id") long userId,
            @PathVariable("available_quantity") int book_quantity) {
        try {
            BookService bookService = new BookService(bookRepository);

            Book book = bookService.searchBookByTitle(bookTitle);

            Optional<User> user = userRepository.findById(userId);

            if (book != null && user.isPresent()) {

                Book updateBook = book;
                updateBook = bookService.updateAvailablQuantity(updateBook, book_quantity);
                List<Book> books = new ArrayList<>();
                if (updateBook.getAvailable_quantity() >= 0) {
                    bookRepository.save(updateBook);
                    books.add(book);
                    List<Integer> book_quantitys = new ArrayList<>();
                    book_quantitys.add(book_quantity);

                    return new ResponseEntity<>(loanRepository.save(new Loan(user.get(), books, book_quantitys)),
                            HttpStatus.CREATED);

                } else {
                    return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
                }

            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = new ArrayList<>();
            books.addAll(bookRepository.findAll());
            return new ResponseEntity<>(books, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /*
     * aqui el usuario debe de mandar el id del libro que desea prestar y la
     * cantidad de libros que desea prestar
     */
    @PatchMapping("/addBookToLoan/{userId}/{book_quantity}/{title}")
    public ResponseEntity<Loan> addBooktoLoan(@PathVariable("title") String title, @PathVariable("userId") Long id,
            @PathVariable("book_quantity") int book_quantity) {
        try {
            LoanService loanService = new LoanService(loanRepository);
            BookService bookService = new BookService(bookRepository);
            Book book = bookService.searchBookByTitle(title);
            System.out.println("hola");
            Loan loanUser = loanService.searchLoanByIdOfUser(id);
            // List<Integer> book_quantitys=loanUser.getBooks_quantity();
            System.out.println("hola");
            Optional<Book> loanbook = bookRepository.findById(book.getId());
            if (loanUser != null && loanbook.isPresent() && book != null) {
                Book updateBook = loanbook.get();
                updateBook = bookService.updateAvailablQuantity(updateBook, book_quantity);
                if (updateBook.getAvailable_quantity() >= 0) {
                    /* Actualiza la cantidad de lisbros disponibles en el repo */
                    bookRepository.save(updateBook);
                    /*
                     * Actualiza la cantidad de libros que va a prestar
                     */
                    loanUser.getBooks_quantity().add(book_quantity);
                    loanUser.getBooks().add(updateBook);
                    loanRepository.save(loanUser);
                    return new ResponseEntity<>(loanUser, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        try {
            if (user.getRole().toLowerCase().equals("usuario")) {
                User _user = userRepository.save(user);
                return new ResponseEntity<>(_user, HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("book/{title}")
    public ResponseEntity<Book> getBooksByTitle(@PathVariable("title") String title) {
        try {
            BookService bookService = new BookService(bookRepository);

            Book _book = bookService.searchBookByTitle(title);
            if (_book != null) {
                return new ResponseEntity<>(_book, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("bookByAuthor/{author}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable("author") String author) {
        try {
            BookService bookService = new BookService(bookRepository);
            List<Book> _book = bookService.searchBookByAuthor(author);
            if (!_book.isEmpty()) {
                return new ResponseEntity<>(_book, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("bookByGenre/{genre}")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable("genre") String genre) {
        try {
            BookService bookService = new BookService(bookRepository);

            List<Book> _book = bookService.searchBookByGenre(genre);
            if (!_book.isEmpty()) {
                return new ResponseEntity<>(_book, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("bookByPublication_date/{publication_date}")
    public ResponseEntity<List<Book>> getBooksByPublication_date(
            @PathVariable("publication_date") String publication_date) {
        try {
            BookService bookService = new BookService(bookRepository);

            List<Book> _book = bookService.searchBookByPublication_date(publication_date);
            if (!_book.isEmpty()) {
                return new ResponseEntity<>(_book, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("bookByIsbn/{isbn}")
    public ResponseEntity<List<Book>> getBooksByIsbn(@PathVariable("isbn") String isbn) {
        try {
            BookService bookService = new BookService(bookRepository);

            List<Book> _book = bookService.searchBookByIsbn(isbn);
            if (!_book.isEmpty()) {
                return new ResponseEntity<>(_book, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("bookByAvailable_quantity/{available_quantity}")
    public ResponseEntity<List<Book>> getBooksByAvailable_quantity(
            @PathVariable("available_quantity") int available_quantity) {
        try {
            BookService bookService = new BookService(bookRepository);

            List<Book> _book = bookService.searchBookByAvailable_quantity(available_quantity);
            if (!_book.isEmpty()) {
                return new ResponseEntity<>(_book, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("bookByDescription/{description}")
    public ResponseEntity<List<Book>> getBooksByDescription(@PathVariable("description") String description) {
        try {
            BookService bookService = new BookService(bookRepository);

            List<Book> _book = bookService.searchBookByDescription(description);
            if (!_book.isEmpty()) {
                return new ResponseEntity<>(_book, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/user/{id}")
    public ResponseEntity<User> putUser (@RequestBody User user,@PathVariable ("id")long id){
        try {
            Optional<User> userData = userRepository.findById(id);
            /*tener en cuenta que no se podra cambiar id, role ni fecha de registro */
            if (userData.isPresent()) {
                User _user = userData.get();
                _user.setName(user.getName());
                _user.setLastname(user.getLastname());
                _user.setEmail(user.getEmail());    
                _user.setPassword(user.getPassword());

                
                return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

            }
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
