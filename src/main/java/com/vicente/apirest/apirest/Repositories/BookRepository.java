package com.vicente.apirest.apirest.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vicente.apirest.apirest.Entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
