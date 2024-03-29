package kz.alishev.springcourse.repositories;

import kz.alishev.springcourse.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByAuthorContainingIgnoreCase(String name);
}
