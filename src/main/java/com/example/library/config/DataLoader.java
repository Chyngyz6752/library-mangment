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

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Seeding reference data (idempotent)");

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
        ensureAuthor("Aldous Huxley", 1894, 1963, "Author of Brave New World");
        ensureAuthor("Ray Bradbury", 1920, 2012, "Author of Fahrenheit 451");
        ensureAuthor("F. Scott Fitzgerald", 1896, 1940, "Author of The Great Gatsby");
        ensureAuthor("Harper Lee", 1926, 2016, "Author of To Kill a Mockingbird");
        ensureAuthor("Jane Austen", 1775, 1817, "Author of Pride and Prejudice");
        ensureAuthor("Leo Tolstoy", 1828, 1910, "Russian novelist, author of War and Peace");
        ensureAuthor("Mikhail Bulgakov", 1891, 1940, "Author of The Master and Margarita");
        ensureAuthor("William Gibson", 1948, null, "Pioneer of cyberpunk");
        ensureAuthor("Douglas Adams", 1952, 2001, "Author of The Hitchhiker's Guide to the Galaxy");
        ensureAuthor("George R.R. Martin", 1948, null, "Author of A Song of Ice and Fire");
        ensureAuthor("Patrick Rothfuss", 1973, null, "Author of The Kingkiller Chronicle");
        ensureAuthor("Neil Gaiman", 1960, null, "Author of American Gods");
        ensureAuthor("Neal Stephenson", 1959, null, "Author of Snow Crash");
        ensureAuthor("Orson Scott Card", 1951, null, "Author of Ender's Game");

        ensureBook("Dune", "978-0441013593", 1965, 5, 412, "English", "Chilton Books", cats.get("Science Fiction"));
        ensureBook("The Hobbit", "978-0345339683", 1937, 3, 310, "English", "George Allen", cats.get("Fantasy"));
        ensureBook("1984", "978-0451524935", 1949, 4, 328, "English", "Secker & Warburg", cats.get("Dystopian"));
        ensureBook("Crime and Punishment", "978-0140449136", 1866, 2, 671, "Russian", "The Russian Messenger", cats.get("Classic"));
        ensureBook("The Fellowship of the Ring", "978-0618640157", 1954, 2, 423, "English", "Allen & Unwin", cats.get("Fantasy"));
        ensureBook("Foundation", "978-0553803716", 1951, 3, 255, "English", "Gnome Press", cats.get("Science Fiction"));
        ensureBook("Brave New World", "978-0060850524", 1932, 3, 311, "English", "Chatto & Windus", cats.get("Dystopian"));
        ensureBook("Fahrenheit 451", "978-1451673319", 1953, 3, 249, "English", "Ballantine Books", cats.get("Dystopian"));
        ensureBook("The Great Gatsby", "978-0743273565", 1925, 4, 180, "English", "Charles Scribner's Sons", cats.get("Classic"));
        ensureBook("To Kill a Mockingbird", "978-0061120084", 1960, 3, 281, "English", "J. B. Lippincott & Co.", cats.get("Classic"));
        ensureBook("Pride and Prejudice", "978-0141439518", 1813, 2, 432, "English", "T. Egerton, Whitehall", cats.get("Classic"));
        ensureBook("Anna Karenina", "978-0143035008", 1877, 2, 864, "Russian", "The Russian Messenger", cats.get("Classic"));
        ensureBook("War and Peace", "978-0143039990", 1869, 2, 1225, "Russian", "The Russian Messenger", cats.get("Classic"));
        ensureBook("The Master and Margarita", "978-0141180144", 1967, 3, 384, "Russian", "YMCA Press", cats.get("Classic"));
        ensureBook("The Brothers Karamazov", "978-0374528379", 1880, 2, 796, "Russian", "The Russian Messenger", cats.get("Classic"));
        ensureBook("Neuromancer", "978-0441569595", 1984, 3, 271, "English", "Ace Books", cats.get("Science Fiction"));
        ensureBook("The Hitchhiker's Guide to the Galaxy", "978-0345391803", 1979, 4, 224, "English", "Pan Books", cats.get("Science Fiction"));
        ensureBook("A Game of Thrones", "978-0553103540", 1996, 4, 694, "English", "Bantam Spectra", cats.get("Fantasy"));
        ensureBook("The Name of the Wind", "978-0756404741", 2007, 3, 662, "English", "DAW Books", cats.get("Fantasy"));
        ensureBook("American Gods", "978-0380973651", 2001, 3, 465, "English", "William Morrow", cats.get("Fantasy"));
        ensureBook("Snow Crash", "978-0553380958", 1992, 3, 470, "English", "Bantam Books", cats.get("Science Fiction"));
        ensureBook("Ender's Game", "978-0812550702", 1985, 3, 324, "English", "Tor Books", cats.get("Science Fiction"));

        ensureMember("Иван", "Иванов", "ivanov@example.com", "+79990000001", "Москва");
        ensureMember("Aisha", "Nurlanova", "aisha@example.com", "+996700123456", "Бишкек");
        ensureMember("John", "Smith", "john@example.com", "+14155550100", "San Francisco");

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
