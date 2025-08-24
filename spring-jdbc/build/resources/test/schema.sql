DROP TABLE IF EXISTS contacts;

CREATE TABLE contacts (
                          id INT PRIMARY KEY AUTO_INCREMENT, -- или IDENTITY для H2
                          name VARCHAR(255),
                          surname VARCHAR(255),
                          phone_number VARCHAR(255),
                          email VARCHAR(255)
);

