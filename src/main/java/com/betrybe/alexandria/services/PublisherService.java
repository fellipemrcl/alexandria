package com.betrybe.alexandria.services;

import com.betrybe.alexandria.models.entities.Publisher;
import com.betrybe.alexandria.models.repositories.PublisherRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {


  PublisherRepository publisherRepository;

  @Autowired
  public PublisherService(PublisherRepository publisherRepository) {
    this.publisherRepository = publisherRepository;
  }

  public Publisher insertPublisher(Publisher publisher) {
    return this.publisherRepository.save(publisher);
  }

  public Optional<Publisher> updatePublisher(Long id, Publisher publisher) {
    Optional<Publisher> optionalPublisher = publisherRepository.findById(id);

    if (optionalPublisher.isPresent()) {
      Publisher publisherFromDB = optionalPublisher.get();
      publisherFromDB.setName(publisher.getName());
      publisherFromDB.setAddress(publisher.getAddress());

      Publisher updatedPublisher = publisherRepository.save(publisherFromDB);
      return Optional.of(updatedPublisher);
    }

    return optionalPublisher;
  }

  public Optional<Publisher> removePublisherById(Long id) {
    Optional<Publisher> optionalPublisher = publisherRepository.findById(id);

    if (optionalPublisher.isPresent()) {
      publisherRepository.deleteById(id);
    }

    return optionalPublisher;
  }

  public Optional<Publisher> getPublisherById(Long id) {
    return publisherRepository.findById(id);
  }

  public Iterable<Publisher> getAllPublishers() {
    return publisherRepository.findAll();
  }
}
