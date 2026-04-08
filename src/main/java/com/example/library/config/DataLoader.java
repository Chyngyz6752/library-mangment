package com.example.library.config;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.entity.Category;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            Category sciFi = categoryRepository.save(new Category(null, "Science Fiction", "Sci-Fi"));
            Category fantasy = categoryRepository.save(new Category(null, "Fantasy", "Magic"));
            Category classic = categoryRepository.save(new Category(null, "Classic", "Timeless"));
            Category dystopian = categoryRepository.save(new Category(null, "Dystopian", "Dark future")); // Добавил Dystopian

            authorRepository.save(new Author(null, "Frank Herbert", 1920, 1986, "Dune author"));
            authorRepository.save(new Author(null, "J.R.R. Tolkien", 1892, 1973, "Hobbit author"));
            authorRepository.save(new Author(null, "George Orwell", 1903, 1950, "1984 author")); // Добавил Orwell

            saveBook("Dune", "978-0441013593", 1965, 5, 412, "English", "Chilton Books", sciFi);
            saveBook("The Hobbit", "978-0345339683", 1937, 3, 310, "English", "George Allen", fantasy);
            saveBook("1984", "978-0451524935", 1949, 4, 328, "English", "Secker & Warburg", dystopian);
            saveBook("Crime and Punishment", "978-0140449136", 1866, 5, 671, "Russian", "The Russian Messenger", classic);
            saveBook("The Fellowship of the Ring", "978-0618640157", 1954, 2, 423, "English", "Allen & Unwin", fantasy);
            saveBook("Foundation", "978-0553803716", 1951, 3, 255, "English", "Gnome Press", sciFi);
        }
    }

    private void saveBook(String title, String isbn, int year, int copies, int pages, String lang, String pub, Category cat) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPublishYear(year);
        book.setTotalCopies(copies);
        book.setPages(pages);
        book.setLanguage(lang);
        book.setPublisher(pub);
        book.setCategory(cat);
        book.setCreatedAt(LocalDateTime.now());
        bookRepository.save(book);
    }
}