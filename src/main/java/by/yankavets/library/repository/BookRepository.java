package by.yankavets.library.repository;

import by.yankavets.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBookByTitleStartingWithIgnoreCase(String startWith);

}
