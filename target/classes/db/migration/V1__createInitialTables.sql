CREATE TABLE persons(
  id SERIAL PRIMARY KEY,
  full_name varchar(50) NOT NULL,
  email varchar(50) NOT NULL
);

CREATE TABLE items(
  id SERIAL PRIMARY KEY,
  title varchar(100) NOT NULL,
  info varchar(100) NOT NULL,
  cost numeric NOT NULL CHECK(cost > 0),
  owner_id INT NOT NULL REFERENCES persons(id)
);

CREATE TABLE loans(
  id SERIAL PRIMARY KEY,
  lender_id INT NOT NULL REFERENCES persons(id),
  item_id INT NOT NULL REFERENCES items(id),
  issued TIMESTAMP NOT NULL,
  deadline TIMESTAMP NOT NULL,
  terms TEXT NOT NULL
);