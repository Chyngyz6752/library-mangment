-- Initial data for Library Management System
-- Run this script in PostgreSQL to populate the database
-- Clears existing data and resets sequences to start from 1

-- Clear existing data
DELETE FROM loans;
DELETE FROM book_copies;
DELETE FROM books;
DELETE FROM authors;
DELETE FROM categories;
DELETE FROM members;

-- Reset sequences to start from 1
ALTER SEQUENCE categories_category_id_seq RESTART WITH 1;
ALTER SEQUENCE authors_author_id_seq RESTART WITH 1;
ALTER SEQUENCE books_book_id_seq RESTART WITH 1;
ALTER SEQUENCE book_copies_copy_id_seq RESTART WITH 1;
ALTER SEQUENCE members_member_id_seq RESTART WITH 1;
ALTER SEQUENCE loans_loan_id_seq RESTART WITH 1;

-- Insert Categories
INSERT INTO categories (name, description) VALUES 
('Science Fiction', 'Sci-Fi literature'),
('Fantasy', 'Fantasy and magic'),
('Classic', 'Timeless classics'),
('Dystopian', 'Dark futures')
ON CONFLICT (name) DO NOTHING;

-- Insert Authors
INSERT INTO authors (full_name, birth_year, death_year, biography) VALUES 
('Frank Herbert', 1920, 1986, 'Author of Dune'),
('J.R.R. Tolkien', 1892, 1973, 'Author of The Hobbit and LOTR'),
('George Orwell', 1903, 1950, 'Author of 1984'),
('Fyodor Dostoevsky', 1821, 1881, 'Russian novelist'),
('Isaac Asimov', 1920, 1992, 'Science fiction grandmaster')
ON CONFLICT (full_name) DO NOTHING;

-- Insert Books
INSERT INTO books (title, isbn, publish_year, total_copies, pages, language, publisher, category_id, created_at) VALUES 
('Dune', '978-0441013593', 1965, 5, 412, 'English', 'Chilton Books', 1, CURRENT_TIMESTAMP),
('The Hobbit', '978-0345339683', 1937, 3, 310, 'English', 'George Allen', 2, CURRENT_TIMESTAMP),
('1984', '978-0451524935', 1949, 4, 328, 'English', 'Secker & Warburg', 4, CURRENT_TIMESTAMP),
('Crime and Punishment', '978-0140449136', 1866, 2, 671, 'Russian', 'The Russian Messenger', 3, CURRENT_TIMESTAMP),
('Foundation', '978-0553803716', 1951, 3, 255, 'English', 'Gnome Press', 1, CURRENT_TIMESTAMP)
ON CONFLICT (isbn) DO NOTHING;

-- Insert Book Copies
INSERT INTO book_copies (book_id, barcode, accession_number, status, location, added_at) VALUES 
(1, 'BC-1-1', 'AN-1-1', 'AVAILABLE', 'Shelf-2', CURRENT_TIMESTAMP),
(1, 'BC-1-2', 'AN-1-2', 'AVAILABLE', 'Shelf-2', CURRENT_TIMESTAMP),
(1, 'BC-1-3', 'AN-1-3', 'AVAILABLE', 'Shelf-2', CURRENT_TIMESTAMP),
(1, 'BC-1-4', 'AN-1-4', 'AVAILABLE', 'Shelf-2', CURRENT_TIMESTAMP),
(1, 'BC-1-5', 'AN-1-5', 'AVAILABLE', 'Shelf-2', CURRENT_TIMESTAMP),
(2, 'BC-2-1', 'AN-2-1', 'AVAILABLE', 'Shelf-3', CURRENT_TIMESTAMP),
(2, 'BC-2-2', 'AN-2-2', 'AVAILABLE', 'Shelf-3', CURRENT_TIMESTAMP),
(2, 'BC-2-3', 'AN-2-3', 'AVAILABLE', 'Shelf-3', CURRENT_TIMESTAMP),
(3, 'BC-3-1', 'AN-3-1', 'AVAILABLE', 'Shelf-4', CURRENT_TIMESTAMP),
(3, 'BC-3-2', 'AN-3-2', 'AVAILABLE', 'Shelf-4', CURRENT_TIMESTAMP),
(3, 'BC-3-3', 'AN-3-3', 'AVAILABLE', 'Shelf-4', CURRENT_TIMESTAMP),
(3, 'BC-3-4', 'AN-3-4', 'AVAILABLE', 'Shelf-4', CURRENT_TIMESTAMP),
(4, 'BC-4-1', 'AN-4-1', 'AVAILABLE', 'Shelf-5', CURRENT_TIMESTAMP),
(4, 'BC-4-2', 'AN-4-2', 'AVAILABLE', 'Shelf-5', CURRENT_TIMESTAMP),
(5, 'BC-5-1', 'AN-5-1', 'AVAILABLE', 'Shelf-6', CURRENT_TIMESTAMP),
(5, 'BC-5-2', 'AN-5-2', 'AVAILABLE', 'Shelf-6', CURRENT_TIMESTAMP),
(5, 'BC-5-3', 'AN-5-3', 'AVAILABLE', 'Shelf-6', CURRENT_TIMESTAMP)
ON CONFLICT (barcode) DO NOTHING;

-- Insert Member
INSERT INTO members (first_name, last_name, email, phone, address, registration_date, is_active, max_allowed_loans) 
VALUES ('Aisha', 'Nurlanova', 'aisha@example.com', '+996700123456', 'Бишкек', CURRENT_DATE, true, 5)
ON CONFLICT (email) DO NOTHING;
