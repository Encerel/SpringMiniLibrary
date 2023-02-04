package by.yankavets.library.service;

import by.yankavets.library.model.Book;
import by.yankavets.library.model.Person;
import by.yankavets.library.repository.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findPersonById(long id) {
        return personRepository.findById(id);
    }


    @Transactional
    public void update(long id, Person updatedPerson) {
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }

    public List<Book> findBooksByPersonId(long id) {
        Optional<Person> personOptional = personRepository.findById(id);

        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            Hibernate.initialize(person.getBooks());
            for (Book book : person.getBooks()) {

                long differenceInMillis = new Date().getTime() - book.getTakenAt().getTime();
                if (differenceInMillis >= 864000000) {
                    book.setExpired(true);
                }
            }

            return person.getBooks();
        }

        return Collections.emptyList();
    }

    public Optional<Person> findPersonByFullName(String fullName) {
        return personRepository.findPersonByFullName(fullName);
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void delete(long id) {
        personRepository.deleteById(id);
    }

}
