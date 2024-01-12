package com.betrybe.alexandria.services;

import com.betrybe.alexandria.models.entities.Book;
import com.betrybe.alexandria.models.repositories.BookRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public Book insertBook(Book book) {
    return this.bookRepository.save(book);
  }

  public Optional<Book> updateBook(Long id, Book book) {
    Optional<Book> optionalBook = bookRepository.findById(id);

    if (optionalBook.isPresent()) {
      Book bookFromDB = optionalBook.get();
      bookFromDB.setTitle(book.getTitle());
      bookFromDB.setGenre(book.getGenre());

      Book updatedBook = bookRepository.save(bookFromDB);
      return Optional.of(updatedBook);
    }

    return optionalBook;
  }

  public Optional<Book> removeBookById(Long id) {
    Optional<Book> optionalBook = bookRepository.findById(id);

    if (optionalBook.isPresent()) {
      bookRepository.deleteById(id);
    }

    return optionalBook;
  }

  public Optional<Book> getBookById(Long id) {
    return bookRepository.findById(id);
  }

  public Iterable<Book> getAllBooks() {
    return bookRepository.findAll();
  }
}