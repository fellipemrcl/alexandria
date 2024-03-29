package com.betrybe.alexandria.controllers;

import com.betrybe.alexandria.controllers.dto.BookDTO;
import com.betrybe.alexandria.controllers.dto.BookDetailDTO;
import com.betrybe.alexandria.controllers.dto.ResponseDTO;
import com.betrybe.alexandria.models.entities.Book;
import com.betrybe.alexandria.models.entities.BookDetail;
import com.betrybe.alexandria.services.BookService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
public class BookController {

  private final BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping
  public ResponseEntity<ResponseDTO<Book>> createBook(@RequestBody BookDTO bookDTO) {
    Book newBook = bookService.insertBook(bookDTO.toBook());
    ResponseDTO<Book> responseDTO = new ResponseDTO<>("Livro criado com sucesso!", newBook);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @PutMapping("/{bookId}")
  public ResponseEntity<ResponseDTO<Book>> updateBook(@PathVariable Long bookId,
      @RequestBody BookDTO bookDTO) {
    Optional<Book> optionalBook = bookService.updateBook(bookId, bookDTO.toBook());

    if (optionalBook.isEmpty()) {
      ResponseDTO<Book> responseDTO = new ResponseDTO<>(
          String.format("Livro com id %d não encontrado!", bookId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Book> responseDTO = new ResponseDTO<>("Livro atualizado com sucesso!",
        optionalBook.get());
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{bookId}")
  public ResponseEntity<ResponseDTO<Book>> deleteBook(@PathVariable Long bookId) {
    Optional<Book> optionalBook = bookService.removeBookById(bookId);

    if (optionalBook.isEmpty()) {
      ResponseDTO<Book> responseDTO = new ResponseDTO<>(
          String.format("Livro com id %d não encontrado!", bookId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Book> responseDTO = new ResponseDTO<>("Livro removido com sucesso!",
        optionalBook.get());
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{bookId}")
  public ResponseEntity<ResponseDTO<Book>> getBookById(@PathVariable Long bookId) {
    Optional<Book> optionalBook = bookService.getBookById(bookId);

    if (optionalBook.isEmpty()) {
      ResponseDTO<Book> responseDTO = new ResponseDTO<>(
          String.format("Livro com id %d não encontrado!", bookId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Book> responseDTO = new ResponseDTO<>("Livro encontrado com sucesso!",
        optionalBook.get());
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping()
  public List<BookDTO> getAllBooks(
      @RequestParam(required = false, defaultValue = "0") int pageNumber,
      @RequestParam(required = false, defaultValue = "20") int pageSize
  ) {
    return bookService.getAllBooks(pageNumber, pageSize);
  }

  @PostMapping("/{bookId}/details")
  public ResponseEntity<ResponseDTO<BookDetail>> createBookDetail(@PathVariable Long bookId,
      @RequestBody BookDetailDTO bookDetailDTO) {
    Optional<BookDetail> bookDetail = bookService.insertBookDetail(bookId,
        bookDetailDTO.toBookDetail());

    if (bookDetail.isEmpty()) {
      ResponseDTO<BookDetail> responseDTO = new ResponseDTO<>(
          String.format("Livro com id %d não encontrado", bookId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<BookDetail> responseDTO = new ResponseDTO<>("Detalhes do livro criado com sucesso.",
        bookDetail.get());
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @PutMapping("/{bookId}/details/{id}")
  public ResponseEntity<ResponseDTO<BookDetail>> updateBookDetail(@PathVariable Long id,
      @RequestBody BookDetailDTO bookDetailDTO) {
    Optional<BookDetail> optionalBookDetail = bookService.updateBookDetail(id,
        bookDetailDTO.toBookDetail());

    if (optionalBookDetail.isEmpty()) {
      ResponseDTO<BookDetail> responseDTO = new ResponseDTO<>(
          String.format("Detalhe do livro com id %d não encontrado!", id), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<BookDetail> responseDTO = new ResponseDTO<>(
        "Detalhe do livro atualizado com sucesso!",
        optionalBookDetail.get());
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{bookId}/details/{id}")
  public ResponseEntity<ResponseDTO<BookDetail>> deleteBookDetail(@PathVariable Long id) {
    Optional<BookDetail> optionalBookDetail = bookService.removeBookDetailById(id);

    if (optionalBookDetail.isEmpty()) {
      ResponseDTO<BookDetail> responseDTO = new ResponseDTO<>(
          String.format("Detalhe do livro com id %d não encontrado!", id), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<BookDetail> responseDTO = new ResponseDTO<>(
        "Detalhe do livro removido com sucesso!",
        optionalBookDetail.get());
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{bookId}/details/{id}")
  public ResponseEntity<ResponseDTO<BookDetail>> getBookDetailById(
      @PathVariable Long id) {
    Optional<BookDetail> optionalBookDetail = bookService.getBookDetailById(id);

    if (optionalBookDetail.isEmpty()) {
      ResponseDTO<BookDetail> responseDTO = new ResponseDTO<>(
          String.format("Detalhe do livro com id %d não encontrado!", id), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<BookDetail> responseDTO = new ResponseDTO<>(
        "Detalhe do livro encontrado com sucesso!",
        optionalBookDetail.get());
    return ResponseEntity.ok(responseDTO);
  }

  @PutMapping("/{bookId}/publisher/{publisherId}")
  public ResponseEntity<ResponseDTO<Book>> setPublisherFromBook(@PathVariable Long bookId,
      @PathVariable Long publisherId) {
    Optional<Book> optionalBook = bookService.setPublisher(bookId, publisherId);

    if (optionalBook.isEmpty()) {
      ResponseDTO<Book> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrado o livro de ID %d ou a editora de ID %d", bookId,
              publisherId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Book> responseDTO = new ResponseDTO<>(
        "Editora vinculada ao livro com sucesso!", optionalBook.get());
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{bookId}/publisher")
  public ResponseEntity<ResponseDTO<Book>> removePublisherFromBook(@PathVariable Long bookId) {
    Optional<Book> optionalBook = bookService.removePublisher(bookId);
    if (optionalBook.isEmpty()) {
      ResponseDTO<Book> responseDTO = new ResponseDTO<>(
          String.format("Não foi possível remover a editora do livro com id %d", bookId),
          null
      );

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
    }

    ResponseDTO<Book> responseDTO = new ResponseDTO<>(
        String.format("Editora removida do livro de ID %d", bookId),
        optionalBook.get()
    );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
  }

  @PutMapping("/{bookId}/author/{authorId}")
  public ResponseEntity<ResponseDTO<Book>> setAuthor(@PathVariable Long bookId,
      @PathVariable Long authorId) {
    Optional<Book> optionalBook = bookService.setAuthor(bookId, authorId);

    if (optionalBook.isEmpty()) {
      ResponseDTO<Book> responseDTO = new ResponseDTO<>(
          String.format(
              "Não foi possível associar o livro de ID %d com a pessoa autora com o ID %d", bookId,
              authorId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Book> responseDTO = new ResponseDTO<>(
        "Autor vinculado ao livro com sucesso!", optionalBook.get());
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{bookId}/author/{authorId}")
  public ResponseEntity<ResponseDTO<Book>> removeAuthor(@PathVariable Long bookId,
      @PathVariable Long authorId) {
    Optional<Book> optionalBook = bookService.removeAuthor(bookId, authorId);

    if (optionalBook.isEmpty()) {
      ResponseDTO<Book> responseDTO = new ResponseDTO<>(
          String.format(
              "Não foi possível desassociar o livro de ID %d com a pessoa autora com o ID %d",
              bookId,
              authorId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Book> responseDTO = new ResponseDTO<>(
        "Autor desvinculado do livro com sucesso!", optionalBook.get());
    return ResponseEntity.ok(responseDTO);
  }
}