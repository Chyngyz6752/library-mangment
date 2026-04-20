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
        if (categoryRepository.count() > 0) {
            log.info("Seed data already present, skipping DataLoader");
            return;
        }
        log.info("Seeding initial data");

        Category sciFi = saveCategory("Science Fiction", "Sci-Fi literature");
        Category fantasy = saveCategory("Fantasy", "Fantasy and magic");
        Category classic = saveCategory("Classic", "Timeless classics");
        Category dystopian = saveCategory("Dystopian", "Dark futures");

        saveAuthor("Frank Herbert", 1920, 1986, "Author of Dune");
        saveAuthor("J.R.R. Tolkien", 1892, 1973, "Author of The Hobbit and LOTR");
        saveAuthor("George Orwell", 1903, 1950, "Author of 1984");
        saveAuthor("Fyodor Dostoevsky", 1821, 1881, "Russian novelist");
        saveAuthor("Isaac Asimov", 1920, 1992, "Science fiction grandmaster");

        Book dune = saveBook("Dune", "978-0441013593", 1965, 5, 412, "English", "Chilton Books", sciFi);
        Book hobbit = saveBook("The Hobbit", "978-0345339683", 1937, 3, 310, "English", "George Allen", fantasy);
        Book nineteen = saveBook("1984", "978-0451524935", 1949, 4, 328, "English", "Secker & Warburg", dystopian);
        Book crime = saveBook("Crime and Punishment", "978-0140449136", 1866, 2, 671, "Russian", "The Russian Messenger", classic);
        Book fellowship = saveBook("The Fellowship of the Ring", "978-0618640157", 1954, 2, 423, "English", "Allen & Unwin", fantasy);
        Book foundation = saveBook("Foundation", "978-0553803716", 1951, 3, 255, "English", "Gnome Press", sciFi);

        createCopiesFor(dune);
        createCopiesFor(hobbit);
        createCopiesFor(nineteen);
        createCopiesFor(crime);
        createCopiesFor(fellowship);
        createCopiesFor(foundation);

        saveMember("Иван", "Иванов", "ivanov@example.com", "+79990000001", "Москва");
        saveMember("Aisha", "Nurlanova", "aisha@example.com", "+996700123456", "Бишкек");
        saveMember("John", "Smith", "john@example.com", "+14155550100", "San Francisco");

        log.info("Seeding done: {} books, {} copies, {} members",
                bookRepository.count(), bookCopyRepository.count(), memberRepository.count());
    }

    private Category saveCategory(String name, String description) {
        Category c = new Category();
        c.setName(name);
        c.setDescription(description);
        return categoryRepository.save(c);
    }

    private Author saveAuthor(String fullName, Integer birth, Integer death, String bio) {
        Author a = new Author();
        a.setFullName(fullName);
        a.setBirthYear(birth);
        a.setDeathYear(death);
        a.setBiography(bio);
        return authorRepository.save(a);
    }

    private Book saveBook(String title, String isbn, int year, int copies, int pages,
                          String lang, String pub, Category cat) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPublishYear(year);
        book.setTotalCopies(copies);
        book.setPages(pages);
        book.setLanguage(lang);
        book.setPublisher(pub);
        book.setCategory(cat);
        return bookRepository.save(book);
    }

    private void createCopiesFor(Book book) {
        int n = book.getTotalCopies() == null ? 1 : book.getTotalCopies();
        for (int i = 1; i <= n; i++) {
            BookCopy c = new BookCopy();
            c.setBook(book);
            c.setBarcode("BC-" + book.getBookId() + "-" + i);
            c.setAccessionNumber("AN-" + book.getBookId() + "-" + i);
            c.setStatus(BookCopyStatus.AVAILABLE);
            c.setLocation("Shelf-" + ((book.getBookId() % 10) + 1));
            bookCopyRepository.save(c);
        }
    }

    private Member saveMember(String firstName, String lastName, String email, String phone, String address) {
        Member m = new Member();
        m.setFirstName(firstName);
        m.setLastName(lastName);
        m.setEmail(email);
        m.setPhone(phone);
        m.setAddress(address);
        return memberRepository.save(m);
    }
}
