CREATE TABLE address (
                    address_id SERIAL PRIMARY KEY,
                    country_id INTEGER,
                    city_id INTEGER,
                    street_id INTEGER,
                    house_number INTEGER,
                    apartment_number INTEGER
);


CREATE TABLE country (
                    country_id SERIAL PRIMARY KEY,
                    address_id INTEGER REFERENCES address(address_id),
                    country_name VARCHAR(100)
);

CREATE TABLE city (
                    city_id SERIAL PRIMARY KEY,
                    address_id INTEGER REFERENCES address(address_id),
                    city_name VARCHAR(100)
);


CREATE TABLE street (
                      street_id SERIAL PRIMARY KEY,
                      address_id INTEGER REFERENCES address(address_id),
                      street_name VARCHAR(100)
);


INSERT INTO address (country_id, city_id, street_id, house_number, apartment_number)
VALUES
    (1,1, 1, 6, 5),
    (1, 1, 2, 6, 7),
    (1, 1, 3, 6, 7),
    (4, 4, 4, 15, 666);



INSERT INTO country (address_id, country_name)
VALUES
    (1, 'USA'),
    (2, 'USA'),
    (3, 'USA'),
    (4, 'The Seven Kingdom');


INSERT INTO city (address_id, city_name)
VALUES
    (1, 'California'),
    (2, 'California'),
    (3, 'Indiana'),
    (4, 'Королевство Севера Westeros');

INSERT INTO street (address_id, street_name)
VALUES
    (1, 'Сайпресс-Бич'),
    (2, 'Сан-Диего'),
    (3, 'Гриндейл'),
    (4, 'Винтерфелл');
