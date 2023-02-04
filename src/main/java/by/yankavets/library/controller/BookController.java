package by.yankavets.library.controller;

import by.yankavets.library.model.Book;
import by.yankavets.library.model.Person;
import by.yankavets.library.service.BookService;
import by.yankavets.library.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;


    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(name = "sort_per_year", required = false) boolean sortByYear,
                        @RequestParam(name = "page_number", required = false) Integer pageNumber,
                        @RequestParam(name = "books_per_page", required = false) Integer booksPerPage) {

        if (pageNumber != null && booksPerPage != null) {
            model.addAttribute("books", bookService.findWithPagination(pageNumber, booksPerPage, sortByYear));
        } else {
            List<Book> books = bookService.findAll(sortByYear);
            model.addAttribute("books", books);
        }
        return "books/index";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("book") Book book) {
        return "books/new-book";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new-book";
        }
        bookService.save(book);
        return "redirect: /books";
    }

    @GetMapping("/{id}")
    public String book(@PathVariable("id") long id, Model model, @ModelAttribute("person") Person person) {
        Optional<Book> book = bookService.findBookById(id);


        if (book.isEmpty()) {
            model.addAttribute("message", "Such book no exists");
            return "redirect: /books/index";
        }

        Optional<Person> bookReader = bookService.findBookReader(book.get().getId());


        if (bookReader.isPresent()) {
            model.addAttribute("bookOwner", bookReader.get());
        } else  {
            model.addAttribute("people", personService.findAll());
        }

        model.addAttribute("book", book.get());
        return "books/book";

    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        Optional<Book> book = bookService.findBookById(id);

        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "books/edit";
        }

        model.addAttribute("message", "Such book no exists");
        return "redirect: /books/index";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("book") Book updatedBook) {

        bookService.update(id, updatedBook);

        return "redirect: /books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        bookService.delete(id);
        return "redirect: /books";
    }

    @PatchMapping("/{bookId}/release")
    public String takeBookFromPerson(@PathVariable("bookId") long id) {
        bookService.takeBookFromPerson(id);
        return "redirect: /books/" + id;
    }

    @PatchMapping("/{bookId}/assign")
    public String assignBookToPerson(@PathVariable("bookId") long bookId,
                                     @ModelAttribute("person") Person selectedPerson) {

        bookService.assignBookToPerson(bookId, selectedPerson);
        return "redirect: /books/" + bookId;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(@RequestParam(value = "query", required = false) String query,
                             Model model) {

            List<Book> books = bookService.findBookByTitleStartingWith(query);
            model.addAttribute("books", books);
            return "books/search";
    }

}
