package by.yankavets.library.service;

import by.yankavets.library.model.Book;
import by.yankavets.library.model.Person;
import by.yankavets.library.repository.BookRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(boolean sortByYear) {

        if (sortByYear) {
          return bookRepository.findAll(Sort.by("releaseDate"));
        }

        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(long id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Person> findBookReader(long bookId) {
        return bookRepository.findById(bookId).map(Book::getReader);
    }

    @Transactional
    public void takeBookFromPerson(long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        bookOptional.ifPresent(book -> book.setReader(null));
    }

    @Transactional
    public void assignBookToPerson(long bookId, Person selectedPerson) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setReader(selectedPerson);
            book.setTakenAt(new Date());
        }

    }

    public List<Book> findWithPagination(Integer pageNumber, Integer booksPerPage, boolean sortByYear) {

        if (sortByYear) {
            return bookRepository.findAll(PageRequest.of(pageNumber, booksPerPage, Sort.by("releaseDate"))).getContent();
        }

        return bookRepository.findAll(PageRequest.of(pageNumber, booksPerPage)).getContent();
    }

    public List<Book> findBookByTitleStartingWith(String startingWith) {

        if (startingWith == null) {
            return Collections.emptyList();
        }

        List<Book> books = bookRepository.findBookByTitleStartingWith(startingWith);

        for (Book book : books) {
            Hibernate.initialize(book.getReader());
        }
        return books;
    }
}
