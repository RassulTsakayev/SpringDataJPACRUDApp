package kz.alishev.springcourse.controllers;

import kz.alishev.springcourse.models.Book;
import kz.alishev.springcourse.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "sort_by_year", required = false)Boolean sortByYear,
                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                        @RequestParam(value = "size", required = false, defaultValue = "10") int size){

       model.addAttribute("books", bookService.findAll(sortByYear, PageRequest.of(page, size)));
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping
    public String saveBook(@ModelAttribute("book") @Valid Book book,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/new";
        }

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "partialName", required = false) String partialName, Model model) {
        // Вызываем метод сервиса для поиска книг по частичному совпадению с названием
        if(partialName != null) {
            List<Book> book = bookService.findByPartialName(partialName);

            // Передаем результаты поиска в модель для отображения на странице
            model.addAttribute("books", book);
        }
        // Возвращаем имя представления (HTML-страницы), которую нужно отобразить
        return "/books/search";
    }

}
