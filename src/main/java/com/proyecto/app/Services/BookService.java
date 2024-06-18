package com.proyecto.app.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proyecto.app.Domain.Book;
import com.proyecto.app.Repository.BookRepository;
@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book searchBookByTitle(String title) {
      
        List<Book> books = new ArrayList<>();

        books.addAll(bookRepository.findAll());
        
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }   

        return null;

    }

    public List<Book> searchBookByAuthor(String author) {
        List<Book> books = new ArrayList<>();

        books.addAll(bookRepository.findAll());

        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                return books;
            }
        }

        return null;
    }

    public List<Book> searchBookByGenre(String genre) {
        List<Book> books = new ArrayList<>();

        books.addAll(bookRepository.findAll());

        for (Book book : books) {
            if (book.getGenre().equals(genre)) {
                return books;
            }
        }

        return null;

    }

    public List<Book> searchBookByPublication_date(String publication_date) {
        List<Book> books = new ArrayList<>();

        books.addAll(bookRepository.findAll());

        for (Book book : books) {
            if (book.getPublication_date().equals(publication_date)) {
                return books;
            }
        }

        return null;

    }

    public List<Book> searchBookByIsbn(String isbn) {
        List<Book> books = new ArrayList<>();

        books.addAll(bookRepository.findAll());

        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return books;
            }
        }

        return null;

    }

    public List<Book> searchBookByAvailable_quantity(int available_quantity) {
        List<Book> books = new ArrayList<>();

        books.addAll(bookRepository.findAll());

        for (Book book : books) {
            if (book.getAvailable_quantity()==(available_quantity)) {
                return books;
            }
        }

        return null;

    }

    public List<Book> searchBookByDescription(String description) {
        List<Book> books = new ArrayList<>();

        books.addAll(bookRepository.findAll());

        for (Book book : books) {
            if (book.getDescription().equals(description)) {
                return books;
            }
        }

        return null;

    }
    public Book  updateAvailablQuantity(Book book,int available_quantity){
        book.setAvailable_quantity((book.getAvailable_quantity()-available_quantity));
        return book;
    }

}
