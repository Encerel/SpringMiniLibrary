package by.yankavets.library.util;

import by.yankavets.library.model.Person;

import by.yankavets.library.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;


        Optional<Person> personFromDB = personService.findPersonByFullName(person.getFullName());

        if (personFromDB.isPresent()) {
            errors.rejectValue("fullName", "500", "This name is already taken!");
        }

        if (person.getYearOfBirth() > 2023 || person.getYearOfBirth() < 1900) {
            errors.rejectValue("yearOfBirth", "", "Birth date should be between 1900 and " + LocalDate.now().getYear());
        }
    }
}
