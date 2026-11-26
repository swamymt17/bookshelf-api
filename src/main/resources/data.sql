-- Sample seed data
INSERT INTO books (title, author, isbn, price, published_year, description, created_at, updated_at)
VALUES
  ('Effective Java', 'Joshua Bloch', '978-0134685991', 45.99, 2018, 'Best practices for Java programming', NOW(), NOW()),
  ('Clean Code', 'Robert C. Martin', '978-0132350884', 39.99, 2008, 'A handbook of agile software craftsmanship', NOW(), NOW()),
  ('Spring in Action', 'Craig Walls', '978-1617294945', 49.99, 2018, 'Comprehensive guide to Spring framework', NOW(), NOW()),
  ('Design Patterns', 'Gang of Four', '978-0201633610', 54.99, 1994, 'Elements of reusable object-oriented software', NOW(), NOW()),
  ('The Pragmatic Programmer', 'David Thomas', '978-0135957059', 42.99, 2019, 'Your journey to mastery', NOW(), NOW());
