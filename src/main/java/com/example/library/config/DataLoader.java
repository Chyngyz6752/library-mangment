package com.example.library.config;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.entity.BookCopy;
import com.example.library.entity.Category;
import com.example.library.entity.Member;
import com.example.library.enums.BookCopyStatus;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookCopyRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.CategoryRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    @Override
    @Transactional
    public void run(String... args) {
        // Полная очистка БД при старте, затем эталонный сид.
        // Гарантирует, что данные в БД (а значит и на фронте, и в API) совпадают с этим кодом,
        // а не накапливаются от прошлых запусков (ddl-auto=update не удаляет старые строки).
        log.info("Clearing database before seeding");
        loanRepository.deleteAllInBatch();
        bookCopyRepository.deleteAllInBatch();
        bookRepository.deleteAllInBatch();
        authorRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();

        log.info("Seeding reference data");

        Map<String, Category> cats = new HashMap<>();
        cats.put("Science Fiction", ensureCategory("Science Fiction", "Sci-Fi literature"));
        cats.put("Fantasy", ensureCategory("Fantasy", "Fantasy and magic"));
        cats.put("Classic", ensureCategory("Classic", "Timeless classics"));
        cats.put("Dystopian", ensureCategory("Dystopian", "Dark futures"));

        ensureAuthor("Frank Herbert", 1920, 1986, "Author of Dune");
        ensureAuthor("J.R.R. Tolkien", 1892, 1973, "Author of The Hobbit and LOTR");
        ensureAuthor("George Orwell", 1903, 1950, "Author of 1984");
        ensureAuthor("Fyodor Dostoevsky", 1821, 1881, "Russian novelist");
        ensureAuthor("Isaac Asimov", 1920, 1992, "Science fiction grandmaster");

        ensureBook("Dune", "978-0441013593", 1965, 5, 412, "English", "Chilton Books", cats.get("Science Fiction"));
        ensureBook("The Hobbit", "978-0345339683", 1937, 3, 310, "English", "George Allen", cats.get("Fantasy"));
        ensureBook("1984", "978-0451524935", 1949, 4, 328, "English", "Secker & Warburg", cats.get("Dystopian"));
        ensureBook("Crime and Punishment", "978-0140449136", 1866, 2, 671, "Russian", "The Russian Messenger", cats.get("Classic"));
        ensureBook("Foundation", "978-0553803716", 1951, 3, 255, "English", "Gnome Press", cats.get("Science Fiction"));

        ensureMember("Aisha", "Nurlanova", "aisha@example.com", "+996700123456", "Бишкек");

        log.info("Seed complete: {} books, {} copies, {} members",
                bookRepository.count(), bookCopyRepository.count(), memberRepository.count());
    }

    private Category ensureCategory(String name, String description) {
        return categoryRepository.findAll().stream()
                .filter(c -> name.equals(c.getName()))
                .findFirst()
                .orElseGet(() -> {
                    Category c = new Category();
                    c.setName(name);
                    c.setDescription(description);
                    return categoryRepository.save(c);
                });
    }

    private void ensureAuthor(String fullName, Integer birth, Integer death, String bio) {
        boolean exists = authorRepository.findAll().stream()
                .anyMatch(a -> fullName.equals(a.getFullName()));
        if (exists) return;
        Author a = new Author();
        a.setFullName(fullName);
        a.setBirthYear(birth);
        a.setDeathYear(death);
        a.setBiography(bio);
        authorRepository.save(a);
    }

    private void ensureBook(String title, String isbn, int year, int copies, int pages,
                            String lang, String publisher, Category category) {
        if (bookRepository.existsByIsbn(isbn)) return;

        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPublishYear(year);
        book.setTotalCopies(copies);
        book.setPages(pages);
        book.setLanguage(lang);
        book.setPublisher(publisher);
        book.setCategory(category);
        Book saved = bookRepository.save(book);

        for (int i = 1; i <= copies; i++) {
            BookCopy c = new BookCopy();
            c.setBook(saved);
            c.setBarcode("BC-" + saved.getBookId() + "-" + i);
            c.setAccessionNumber("AN-" + saved.getBookId() + "-" + i);
            c.setStatus(BookCopyStatus.AVAILABLE);
            c.setLocation("Shelf-" + ((saved.getBookId() % 10) + 1));
            bookCopyRepository.save(c);
        }
    }

    private void ensureMember(String firstName, String lastName, String email, String phone, String address) {
        if (memberRepository.existsByEmail(email)) return;
        Member m = new Member();
        m.setFirstName(firstName);
        m.setLastName(lastName);
        m.setEmail(email);
        m.setPhone(phone);
        m.setAddress(address);
        memberRepository.save(m);
    }
}
