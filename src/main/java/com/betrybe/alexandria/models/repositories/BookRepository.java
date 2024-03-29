package com.betrybe.alexandria.models.repositories;

import com.betrybe.alexandria.models.entities.Author;
import com.betrybe.alexandria.models.entities.Book;
import com.betrybe.alexandria.models.entities.Publisher;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  // Optional<Book> findByPublisher(Publisher publisher);

  // List<Book> findBookByAuthorsName(Author author);

  Page<Book> findAll(Pageable pageable);
}
