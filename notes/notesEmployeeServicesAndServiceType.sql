-- Создание таблицы типов услуг
CREATE TABLE pets_owners_sheme.service_types (
                               id SERIAL PRIMARY KEY,
                               code VARCHAR(50) UNIQUE NOT NULL,
                               name VARCHAR(100) NOT NULL,
                               description TEXT
);

-- Создание таблицы сотрудников
CREATE TABLE pets_owners_sheme.employee (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          specialization VARCHAR(255)
);

-- Создание таблицы услуг
CREATE TABLE pets_owners_sheme.medicalServices (
                          id SERIAL PRIMARY KEY,
                          service_type_id INTEGER NOT NULL,
                          price NUMERIC(10, 2),
                          description TEXT,
                          FOREIGN KEY (service_type_id) REFERENCES service_types(id) ON DELETE RESTRICT
);

-- Создание таблицы связи сотрудников и услуг
CREATE TABLE pets_owners_sheme.employee_services (
                                   employee_id INTEGER NOT NULL,
                                   service_id INTEGER NOT NULL,
                                   PRIMARY KEY (employee_id, service_id),
                                   FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE,
                                   FOREIGN KEY (service_id) REFERENCES medicalServices(id) ON DELETE CASCADE
);

-- Создание индексов
-- idx_service_type_code - ускоряет поиск по коду типа услуги (используется для быстрого доступа)
CREATE INDEX idx_service_type_code ON pets_owners_sheme.service_types(code);
-- idx_services_type - оптимизирует соединения по типу услуги
CREATE INDEX idx_services_type ON pets_owners_sheme.medicalServices(service_type_id);
-- idx_employee_name - ускоряет поиск сотрудников по имени
CREATE INDEX idx_employee_name ON pets_owners_sheme.employee(name);
-- idx_employee_services_emp - оптимизирует запросы вида "какие услуги может оказывать сотрудник X"
CREATE INDEX idx_employee_services_emp ON pets_owners_sheme.employee_services(employee_id);
-- idx_employee_services_svc - оптимизирует запросы вида "какие сотрудники оказывают услугу Y"
CREATE INDEX idx_employee_services_svc ON pets_owners_sheme.employee_services(service_id);

-- Заполнение таблицы типов услуг
-- INSERT INTO service_types (code, name, description) VALUES
--                                                        ('VACCINATION', 'Вакцинация', 'Плановые и экстренные прививки'),
--                                                        ('CHIPPING', 'Чипирование', 'Имплантация микрочипов для идентификации'),
--                                                        ('DIAGNOSTICS', 'Диагностика', 'Комплексное обследование животных'),
--                                                        ('SURGERY', 'Хирургия', 'Оперативные вмешательства'),
--                                                         ('GROOMING', 'Груминг', 'Гигиенический уход за животными'),
--                                                         ('OTHER', 'Другие услуги', 'Прочие ветеринарные услуги');

-- Заполнение таблицы сотрудников
--INSERT INTO employee (name, specialization) VALUES
--                                                 ('Иванов Иван Иванович', 'Ветеринар-терапевт'),
--                                                 ('Петрова Полина Петровна', 'Ветеринар-хирург'),
--                                                 ('Сидоров Сергей Сергеевич', 'Ветеринар-дерматолог'),
--                                                 ('Кузнецова Анна Михайловна', 'Ветеринар-кардиолог'),
--                                                 ('Смирнов Дмитрий Олегович', 'Грумер');
--
-- Заполнение таблицы услуг
-- INSERT INTO medicalServices (service_type_id, price, description) VALUES
--                                                                (1, 1500.00, 'Комплексная ежегодная вакцинация'),
--                                                                (1, 2000.00, 'Экстренная вакцинация от бешенства'),
--                                                                (2, 2500.00, 'Чипирование с внесением в международный реестр'),
--                                                                (2, 1800.00, 'Чипирование без регистрации'),
--                                                                (3, 3500.00, 'Полная диагностика состояния животного'),
--                                                                (3, 2500.00, 'Экспресс-диагностика'),
--                                                                (4, 8000.00, 'Стерилизация/кастрация'),
--                                                                (4, 12000.00, 'Сложная хирургическая операция'),
--                                                                (5, 2000.00, 'Комплексный груминг собак'),
--                                                                (5, 1500.00, 'Гигиеническая стрижка кошек'),
--                                                                (6, 1000.00, 'Консультация ветеринара'),
--                                                                (6, 500.00, 'Обработка от паразитов');
--
-- Заполнение таблицы связи сотрудников и услуг
-- INSERT INTO employee_services (employee_id, service_id) VALUES
-- -- Иванов (терапевт)
-- (1, 1), (1, 2), (1, 11),
-- -- Петрова (хирург)
-- (2, 3), (2, 4), (2, 7), (2, 8), (2, 5), (2, 6),
-- -- Сидоров (дерматолог)
-- (3, 5), (3, 6), (3, 11),
-- -- Кузнецова (кардиолог)
-- (4, 5), (4, 6),
-- -- Смирнов (грумер)
-- (5, 9), (5, 10);

-- Обновление последовательностей после ручного задания ID

--SELECT setval('service_types_id_seq', (SELECT MAX(id) FROM service_types));
--SELECT setval('employee_id_seq', (SELECT MAX(id) FROM employee));
--SELECT setval('services_id_seq', (SELECT MAX(id) FROM medicalServices));


--Функция setval() явно устанавливает текущее значение последовательности равным максимальному ID в таблице.
--service_types_id_seq - имя последовательности для таблицы service_types
--SELECT MAX(id) FROM service_types - находит максимальный существующий ID
--setval() устанавливает последовательность на это значение + 1