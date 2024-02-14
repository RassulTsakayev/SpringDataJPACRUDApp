package kz.alishev.springcourse.services;

import kz.alishev.springcourse.models.Book;
import kz.alishev.springcourse.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(Boolean sortByYear, PageRequest of){
        List<Book>books = booksRepository.findAll();

        if(sortByYear!=null) {
            Comparator<Book> myFieldComparator = Comparator.comparingInt(Book::getReleaseYear);
            books.sort(myFieldComparator);
        }

        if(Objects.isNull(of)){
            return books;
        }else{
            Page<Book> page= booksRepository.findAll(of);
            return page.getContent();
        }
    }

    public List<Book> findByPartialName(String partialName){
        return booksRepository.findByAuthorContainingIgnoreCase(partialName);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

}
