CREATE TABLE genre
(
  id INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE movie
(
  id INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  adding_date DATE NOT NULL,
  genre_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (genre_id) REFERENCES genre(id)
);
