CREATE TABLE genre
(
  id BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE movie
(
  id BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  adding_date TIMESTAMP(6) NOT NULL,
  genre_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (genre_id) REFERENCES genre(id)
);
