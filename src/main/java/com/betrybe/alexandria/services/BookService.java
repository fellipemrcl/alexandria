package com.betrybe.alexandria.services;

import com.betrybe.alexandria.models.entities.Book;
import com.betrybe.alexandria.models.entities.BookDetail;
import com.betrybe.alexandria.models.repositories.BookDetailRepository;
import com.betrybe.alexandria.models.repositories.BookRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private final BookRepository bookRepository;
  private final BookDetailRepository bookDetailRepository;

  @Autowired
  public BookService(BookRepository bookRepository, BookDetailRepository bookDetailRepository) {
    this.bookRepository = bookRepository;
    this.bookDetailRepository = bookDetailRepository;
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

  public BookDetail insertBookDetail(BookDetail bookDetail) {
    return this.bookDetailRepository.save(bookDetail);
  }

  public Optional<BookDetail> updateBookDetail(Long id, BookDetail bookDetail) {
    Optional<BookDetail> optionalBookDetail = bookDetailRepository.findById(id);

    if (optionalBookDetail.isPresent()) {
      BookDetail bookDetailFromDB = optionalBookDetail.get();
      bookDetailFromDB.setIsbn(bookDetail.getIsbn());
      bookDetailFromDB.setPageCount(bookDetail.getPageCount());
      bookDetailFromDB.setSummary(bookDetail.getSummary());
      bookDetailFromDB.setYear(bookDetail.getYear());

      BookDetail updatedBookDetail = bookDetailRepository.save(bookDetailFromDB);
      return Optional.of(updatedBookDetail);
    }

    return optionalBookDetail;
  }

  public Optional<BookDetail> removeBookDetailById(Long id) {
    Optional<BookDetail> optionalBookDetail = bookDetailRepository.findById(id);

    if (optionalBookDetail.isPresent()) {
      bookDetailRepository.deleteById(id);
    }

    return optionalBookDetail;
  }

  public Optional<BookDetail> getBookDetailById(Long id) {
    return bookDetailRepository.findById(id);
  }
}