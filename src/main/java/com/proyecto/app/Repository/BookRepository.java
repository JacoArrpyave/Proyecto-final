package com.proyecto.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.app.Domain.Book;

public interface    BookRepository extends JpaRepository<Book,Long> {
    

    
}
