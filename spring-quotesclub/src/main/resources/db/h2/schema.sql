DROP TABLE authors IF EXISTS;
DROP TABLE quotes IF EXISTS;
DROP TABLE comments IF EXISTS;


CREATE TABLE authors (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR(30),
  city       VARCHAR(30)
);
CREATE INDEX authors_last_name ON vets (last_name);

CREATE TABLE quotes (
  id   INTEGER IDENTITY PRIMARY KEY,
  author_id INTEGER,
  content   VARCHAR(255)
);
ALTER TABLE quotes ADD CONSTRAINT fk_quotes_authors FOREIGN KEY (author_id) REFERENCES authors (id);
CREATE INDEX authors_author_id ON quotes (author_id);

CREATE TABLE comments (
  id   INTEGER IDENTITY PRIMARY KEY,
  author_id INTEGER NOT NULL,
  quote_id INTEGER NOT NULL,
  content   VARCHAR(255)
);
ALTER TABLE comments ADD CONSTRAINT fk_comments_authors FOREIGN KEY (author_id) REFERENCES authors (id);
ALTER TABLE comments ADD CONSTRAINT fk_comments_quotes FOREIGN KEY (quotes_id) REFERENCES quotes (id);
CREATE INDEX authors_author_id ON quotes (author_id);
