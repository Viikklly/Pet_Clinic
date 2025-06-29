CREATE TABLE owners (
    owner_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20)
);


CREATE TABLE pets (
    pet_id SERIAL PRIMARY KEY,
    owner_id INTEGER REFERENCES owners(owner_id),
    name VARCHAR(100),
    animal_type VARCHAR(50),
    breed VARCHAR(50),
    age INTEGER,
    vaccinations VARCHAR(100)
);


INSERT INTO owners (name, email, phone)
VALUES
    ('Scott Turner', 'sturner@cpd.gov', '911'),
    ('Michael Dooley', 'dooley@cpd.gov', '911'),
    ('Sabrina Spellman', 'iamnotawitch@hotmail.com', '555-0117'),
    ('Jon Snow', 'jonsnow@nightwatch.org', '555-1439'),
    ('Joe Camber', 'castlerockcarservice@hotmail.com', '555-7125'),
    ('Ellie Creed', 'misscreed@yahoo.com', '555-2559'),
    ('Emily Elizabeth', 'ihateflorence@hotmail.com', '555-2559'),
    ('Daenerys Targaryen', 'dracomama@veryhotmail.com', '555-0309'),
    ('Vic', 'itslegalinpostap@somemail.com', '555-1335'),
    ('Yorick Brown', 'lastman@hotmail.com', '555-0001');


INSERT INTO pets (owner_id, name, animal_type, breed, age, vaccinations)
VALUES
    (1, 'Hooch', 'dog', 'french mastiff', 11, 'all'),
    (2, 'Jerry Lee', 'dog', 'german sheperd', 7, 'all'),
    (3, 'Salem', 'cat', 'domestic cat', 500, 'none'),
    (4, 'Ghost', 'dog', 'direwolf', 4, 'unknown'),
    (5, 'Cujo', 'dog', 'saint bernard', 8, 'all, except rabies'),
    (6, 'Church (Winston Churchill)', 'cat', 'domestic cat', 10, 'all'),
    (7, 'Clifford', 'dog', 'big red dog', 2, 'unknown'),
    (8, 'Viserion', 'dragon', 'dragon', 3, 'unknown'),
    (8, 'Rhaegal', 'dragon', 'dragon', 3, 'unknown'),
    (8, 'Drogon', 'dragon', 'dragon', 3, 'unknown'),
    (9, 'Blood', 'dog', 'bobtail', 12, 'none'),
    (10, 'Ampersant', 'monkey', 'capuchin', 4, 'all');