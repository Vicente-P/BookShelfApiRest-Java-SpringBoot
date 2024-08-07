package com.vicente.apirest.apirest.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicente.apirest.apirest.Repositories.BookRepository;
import com.vicente.apirest.apirest.Entities.Book;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("The book with the ID: " + id + " was not found"));
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {      
        Book book = bookRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("The book with the ID: " + id + " was not found"));

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setGenre(bookDetails.getGenre());
        book.setPublicationDate(bookDetails.getPublicationDate());
        book.setPages(bookDetails.getPages());
        book.setLanguage(bookDetails.getLanguage());
        book.setPublisher(bookDetails.getPublisher());
        book.setDescription(bookDetails.getDescription());
        book.setAvailable(bookDetails.isAvailable());
        

        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id){
        Book book = bookRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("The book with the ID: " + id + " was not found"));
        
        bookRepository.delete(book);

        return "The book with the ID: "+ id +" was successfully deleted";

    }
    
    
    
    
    

}
